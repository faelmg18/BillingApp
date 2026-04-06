package com.desafio.billingapp.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.desafio.billing.BillingConnectionState
import com.desafio.billing.PurchaseResult
import com.desafio.billingapp.R
import com.desafio.billingapp.data.local.entity.PurchaseEntity
import com.desafio.billingapp.data.repository.PurchaseRepository
import com.desafio.billingapp.utils.StringResourceProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProductUiState(
    val isLoading: Boolean = false,
    val productDetails: List<ProductDetails> = emptyList(),
    val isPremiumUnlocked: Boolean = false,
    val purchaseHistory: List<PurchaseEntity> = emptyList(),
    val connectionState: BillingConnectionState = BillingConnectionState.DISCONNECTED,
    val error: String? = null,
)

class ProductViewModel(
    private val repository: PurchaseRepository,
    private val strings: StringResourceProvider,
) : ViewModel() {

    companion object {
        val PRODUCT_IDS = listOf("premium_product_001")
    }

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _events = Channel<String>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeConnectionState()
        observePurchaseEvents()
        observeProductDetails()
        observePremiumStatus()
        observePurchaseHistory()
        repository.startBilling()
    }

    fun purchase(activity: Activity, productDetails: ProductDetails?) {
        if (productDetails == null) {
            viewModelScope.launch { _events.send(strings.getString(R.string.event_product_unavailable)) }
            return
        }
        repository.purchase(activity, productDetails)
    }

    fun restorePurchases() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.restorePurchases()
            _uiState.update { it.copy(isLoading = false) }
            _events.send(strings.getString(R.string.event_purchases_restored))
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun observeConnectionState() {
        viewModelScope.launch {
            repository.connectionState.collect { state ->
                _uiState.update { it.copy(connectionState = state) }
                if (state == BillingConnectionState.CONNECTED) loadProducts()
            }
        }
    }

    private fun observePurchaseEvents() {
        viewModelScope.launch {
            repository.purchaseEvents.collect { result ->
                when (result) {
                    is PurchaseResult.Success -> {
                        repository.handleSuccessfulPurchase(result.purchase)
                        _events.send(strings.getString(R.string.event_purchase_success))
                    }

                    is PurchaseResult.Error ->
                        _uiState.update {
                            it.copy(
                                error = strings.getString(
                                    R.string.event_purchase_error,
                                    result.message
                                )
                            )
                        }

                    is PurchaseResult.Cancelled ->
                        _events.send(strings.getString(R.string.event_purchase_cancelled))

                    is PurchaseResult.Pending ->
                        _events.send(strings.getString(R.string.event_purchase_pending))
                }
            }
        }
    }

    private fun observeProductDetails() {
        viewModelScope.launch {
            repository.productDetails.collect { details ->
                _uiState.update { it.copy(productDetails = details) }
            }
        }
    }

    private fun observePremiumStatus() {
        viewModelScope.launch {
            repository.isPremiumUnlocked.collect { isPremium ->
                _uiState.update { it.copy(isPremiumUnlocked = isPremium) }
            }
        }
    }

    private fun observePurchaseHistory() {
        viewModelScope.launch {
            repository.purchaseHistory.collect { history ->
                _uiState.update { it.copy(purchaseHistory = history) }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.loadProducts(PRODUCT_IDS)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.stopBilling()
    }
}
