package com.desafio.billingapp.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.desafio.billingapp.R
import com.desafio.billingapp.ui.components.ConnectionStatusBar
import com.desafio.billingapp.ui.components.HeroBanner
import com.desafio.billingapp.ui.components.ProductCard
import com.desafio.billingapp.ui.components.PurchaseHistoryEmptyState
import com.desafio.billingapp.ui.components.PurchaseHistoryItem
import com.desafio.billingapp.viewmodel.ProductViewModel
import com.desafio.designsystem.components.button.BillingButtonOutlined
import com.desafio.designsystem.components.feedback.BillingErrorCard
import com.desafio.designsystem.components.feedback.BillingSkeletonCard
import com.desafio.designsystem.components.layout.BillingScaffold
import com.desafio.designsystem.components.layout.SectionHeader
import com.desafio.designsystem.theme.BillingThemeTokens
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreen(
    viewModel: ProductViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val spacing = BillingThemeTokens.spacing

    val defaultBenefits = listOf(
        stringResource(R.string.benefit_unlimited_access),
        stringResource(R.string.benefit_no_ads),
        stringResource(R.string.benefit_priority_support),
        stringResource(R.string.benefit_free_updates),
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    BillingScaffold(
        title = stringResource(R.string.app_name),
        snackbarHostState = snackbarHostState,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = spacing.lg),
            verticalArrangement = Arrangement.spacedBy(spacing.lg),
        ) {
            item { Spacer(Modifier.height(spacing.sm)) }

            item {
                HeroBanner(isPremium = uiState.isPremiumUnlocked)
            }

            item {
                ConnectionStatusBar(state = uiState.connectionState)
            }

            uiState.error?.let { error ->
                item {
                    BillingErrorCard(
                        message = error,
                        dismissLabel = stringResource(R.string.label_dismiss),
                        onDismiss = { viewModel.dismissError() },
                    )
                }
            }

            if (uiState.isLoading) {
                item { BillingSkeletonCard() }
            } else if (uiState.productDetails.isNotEmpty()) {
                items(uiState.productDetails) { product ->
                    ProductCard(
                        title = product.name,
                        description = product.description,
                        price = product.oneTimePurchaseOfferDetails?.formattedPrice ?: "-",
                        benefits = defaultBenefits,
                        isPremiumUnlocked = uiState.isPremiumUnlocked,
                        isLoading = uiState.isLoading,
                        onBuyClick = { viewModel.purchase(context as Activity, product) },
                    )
                }
            } else {
                item {
                    ProductCard(
                        title = stringResource(R.string.product_fallback_title),
                        description = stringResource(R.string.product_fallback_description),
                        price = stringResource(R.string.product_fallback_price),
                        benefits = defaultBenefits,
                        isPremiumUnlocked = uiState.isPremiumUnlocked,
                        onBuyClick = { viewModel.purchase(context as Activity, null) },
                    )
                }
            }

            item {
                BillingButtonOutlined(
                    label = stringResource(R.string.btn_restore_purchases),
                    onClick = { viewModel.restorePurchases() },
                    modifier = Modifier.fillParentMaxWidth(),
                )
            }

            item {
                SectionHeader(
                    title = stringResource(R.string.section_purchase_history),
                    subtitle = stringResource(
                        R.string.purchase_history_subtitle,
                        uiState.purchaseHistory.size
                    ),
                )
            }

            if (uiState.purchaseHistory.isEmpty()) {
                item { PurchaseHistoryEmptyState() }
            } else {
                items(uiState.purchaseHistory) { purchase ->
                    PurchaseHistoryItem(
                        productId = purchase.productId,
                        orderId = purchase.orderId ?: "—",
                        purchaseTime = purchase.purchaseTime,
                        isAcknowledged = purchase.isAcknowledged,
                    )
                }
            }

            item { Spacer(Modifier.height(spacing.xl3)) }
        }
    }
}
