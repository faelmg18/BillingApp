package com.desafio.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BillingManagerImpl(private val context: Context) : BillingManager {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _connectionState = MutableStateFlow(BillingConnectionState.DISCONNECTED)
    override val connectionState: StateFlow<BillingConnectionState> = _connectionState.asStateFlow()

    private val _purchaseEvents = MutableSharedFlow<PurchaseResult>(replay = 0)
    override val purchaseEvents: Flow<PurchaseResult> = _purchaseEvents.asSharedFlow()

    private val _productDetails = MutableStateFlow<List<ProductDetails>>(emptyList())
    override val productDetails: StateFlow<List<ProductDetails>> = _productDetails.asStateFlow()

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        scope.launch {
            handlePurchasesUpdated(billingResult, purchases)
        }
    }

    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
        .build()

    override fun startConnection() {
        if (billingClient.isReady) return
        _connectionState.value = BillingConnectionState.CONNECTING

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    _connectionState.value = BillingConnectionState.CONNECTED
                } else {
                    _connectionState.value = BillingConnectionState.DISCONNECTED
                }
            }

            override fun onBillingServiceDisconnected() {
                _connectionState.value = BillingConnectionState.DISCONNECTED
                startConnection()
            }
        })
    }

    override fun endConnection() {
        billingClient.endConnection()
        _connectionState.value = BillingConnectionState.CLOSED
    }

    override suspend fun queryProducts(productIds: List<String>) {
        if (!billingClient.isReady) {
            _productDetails.value = emptyList()
            return
        }

        val productList = productIds.map { productId ->
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        val result = billingClient.queryProductDetails(params)
        _productDetails.value =
            if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                result.productDetailsList ?: emptyList()
            } else {
                emptyList()
            }
    }

    override fun launchPurchaseFlow(activity: Activity, productDetails: ProductDetails) {
        if (!billingClient.isReady) {
            scope.launch {
                _purchaseEvents.emit(
                    PurchaseResult.Error(
                        -1,
                        context.getString(R.string.billing_not_connected)
                    )
                )
            }
            return
        }

        val params = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .build()
                )
            )
            .build()

        val result = billingClient.launchBillingFlow(activity, params)
        if (result.responseCode != BillingClient.BillingResponseCode.OK) {
            scope.launch {
                _purchaseEvents.emit(PurchaseResult.Error(result.responseCode, result.debugMessage))
            }
        }
    }

    override suspend fun queryPurchases(): List<Purchase> {
        if (!billingClient.isReady) return emptyList()

        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        val result = billingClient.queryPurchasesAsync(params)
        return if (result.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            result.purchasesList
        } else {
            emptyList()
        }
    }

    private suspend fun handlePurchasesUpdated(
        billingResult: BillingResult,
        purchases: List<Purchase>?
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.forEach { purchase ->
                    when (purchase.purchaseState) {
                        Purchase.PurchaseState.PURCHASED -> {
                            if (!purchase.isAcknowledged) {
                                acknowledgePurchase(purchase)
                            }
                            _purchaseEvents.emit(PurchaseResult.Success(purchase))
                        }

                        Purchase.PurchaseState.PENDING -> {
                            _purchaseEvents.emit(PurchaseResult.Pending(purchase))
                        }

                        else -> Unit
                    }
                }
            }

            BillingClient.BillingResponseCode.USER_CANCELED -> {
                _purchaseEvents.emit(PurchaseResult.Cancelled)
            }

            else -> {
                _purchaseEvents.emit(
                    PurchaseResult.Error(
                        responseCode = billingResult.responseCode,
                        message = billingResult.debugMessage
                    )
                )
            }
        }
    }

    private suspend fun acknowledgePurchase(purchase: Purchase) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params)
    }
}
