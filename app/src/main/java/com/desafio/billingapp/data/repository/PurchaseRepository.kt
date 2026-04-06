package com.desafio.billingapp.data.repository

import android.app.Activity
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.desafio.billing.BillingConnectionState
import com.desafio.billing.BillingManager
import com.desafio.billing.PurchaseResult
import com.desafio.billingapp.data.local.SecurePreferences
import com.desafio.billingapp.data.local.UserPreferencesDataStore
import com.desafio.billingapp.data.local.dao.PurchaseDao
import com.desafio.billingapp.data.local.entity.PurchaseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class PurchaseRepository(
    private val billingManager: BillingManager,
    private val purchaseDao: PurchaseDao,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val securePreferences: SecurePreferences
) {
    val connectionState: StateFlow<BillingConnectionState> = billingManager.connectionState
    val purchaseEvents: Flow<PurchaseResult> = billingManager.purchaseEvents
    val productDetails: StateFlow<List<ProductDetails>> = billingManager.productDetails
    val purchaseHistory: Flow<List<PurchaseEntity>> = purchaseDao.getAllPurchases()
    val isPremiumUnlocked: Flow<Boolean> = userPreferencesDataStore.isPremiumUnlocked

    fun startBilling() = billingManager.startConnection()
    fun stopBilling() = billingManager.endConnection()

    suspend fun loadProducts(productIds: List<String>) {
        billingManager.queryProducts(productIds)
    }

    fun purchase(activity: Activity, productDetails: ProductDetails) {
        billingManager.launchPurchaseFlow(activity, productDetails)
    }

    suspend fun handleSuccessfulPurchase(purchase: Purchase) {
        purchase.products.forEach { productId ->
            securePreferences.savePurchaseToken(productId, purchase.purchaseToken)
        }

        val entity = PurchaseEntity(
            purchaseToken = purchase.purchaseToken,
            productId = purchase.products.firstOrNull() ?: "",
            orderId = purchase.orderId,
            purchaseTime = purchase.purchaseTime,
            purchaseState = purchase.purchaseState,
            isAcknowledged = purchase.isAcknowledged,
            packageName = purchase.packageName
        )
        purchaseDao.insertPurchase(entity)

        userPreferencesDataStore.setPremiumUnlocked(true)
        purchase.products.firstOrNull()?.let {
            userPreferencesDataStore.setLastProductPurchased(it)
        }
    }

    suspend fun restorePurchases() {
        val purchases = billingManager.queryPurchases()
        purchases.forEach { purchase ->
            if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                handleSuccessfulPurchase(purchase)
            }
        }
    }
}
