package com.desafio.billing

import android.app.Activity
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BillingManager {

    val connectionState: StateFlow<BillingConnectionState>

    val purchaseEvents: Flow<PurchaseResult>

    val productDetails: StateFlow<List<ProductDetails>>

    fun startConnection()

    fun endConnection()

    suspend fun queryProducts(productIds: List<String>)

    fun launchPurchaseFlow(activity: Activity, productDetails: ProductDetails)

    suspend fun queryPurchases(): List<Purchase>
}

enum class BillingConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    CLOSED
}

sealed class PurchaseResult {
    data class Success(val purchase: Purchase) : PurchaseResult()
    data class Error(val responseCode: Int, val message: String) : PurchaseResult()
    object Cancelled : PurchaseResult()
    data class Pending(val purchase: Purchase) : PurchaseResult()
}
