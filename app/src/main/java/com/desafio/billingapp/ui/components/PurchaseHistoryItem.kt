package com.desafio.billingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.desafio.billingapp.R
import com.desafio.designsystem.components.card.BillingCardSurface
import com.desafio.designsystem.components.indicator.BillingStatusChip
import com.desafio.designsystem.theme.BillingThemeTokens
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PurchaseHistoryItem(
    productId: String,
    orderId: String,
    purchaseTime: Long,
    modifier: Modifier = Modifier,
    isAcknowledged: Boolean = true,
) {
    val colors = BillingThemeTokens.colors
    val spacing = BillingThemeTokens.spacing
    val typography = BillingThemeTokens.typography

    val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
        .format(Date(purchaseTime))

    BillingCardSurface(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing.sm),
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(20.dp),
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productId,
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface,
                )
                Text(
                    text = stringResource(R.string.purchase_order_label, orderId),
                    style = typography.labelSmall,
                    color = colors.onSurfaceVariant,
                )
                Text(
                    text = formattedDate,
                    style = typography.labelSmall,
                    color = colors.onSurfaceVariant,
                )
            }

            BillingStatusChip(
                label = stringResource(if (isAcknowledged) R.string.purchase_status_confirmed else R.string.purchase_status_pending),
                dotColor = if (isAcknowledged) colors.success else colors.warning,
            )
        }
    }
}

@Composable
fun PurchaseHistoryEmptyState(modifier: Modifier = Modifier) {
    val colors = BillingThemeTokens.colors
    val spacing = BillingThemeTokens.spacing
    val typography = BillingThemeTokens.typography

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = spacing.xl3),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            tint = colors.outlineVariant,
            modifier = Modifier.size(48.dp),
        )
        Text(
            text = stringResource(R.string.purchase_history_empty),
            style = typography.bodyMedium,
            color = colors.onSurfaceVariant,
        )
    }
}
