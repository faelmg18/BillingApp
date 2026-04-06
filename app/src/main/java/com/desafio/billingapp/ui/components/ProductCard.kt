package com.desafio.billingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.desafio.billingapp.R
import com.desafio.designsystem.components.button.BillingButton
import com.desafio.designsystem.components.button.BillingButtonPremium
import com.desafio.designsystem.components.card.BillingCardPremium
import com.desafio.designsystem.components.indicator.BillingPremiumBadge
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun ProductCard(
    title: String,
    description: String,
    price: String,
    modifier: Modifier = Modifier,
    benefits: List<String> = emptyList(),
    isPremiumUnlocked: Boolean = false,
    isLoading: Boolean = false,
    onBuyClick: () -> Unit = {},
) {
    val colors = BillingThemeTokens.colors
    val spacing = BillingThemeTokens.spacing
    val typography = BillingThemeTokens.typography

    BillingCardPremium(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface,
                modifier = Modifier.weight(1f),
            )
            if (isPremiumUnlocked) BillingPremiumBadge()
        }

        Spacer(Modifier.height(spacing.sm))

        Text(
            text = description,
            style = typography.bodyMedium,
            color = colors.onSurfaceVariant,
        )

        if (benefits.isNotEmpty()) {
            Spacer(Modifier.height(spacing.md))
            benefits.forEach { benefit ->
                Row(
                    modifier = Modifier.padding(vertical = spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.sm),
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = benefit,
                        style = typography.bodyMedium,
                        color = colors.onSurface,
                    )
                }
            }
        }

        Spacer(Modifier.height(spacing.lg))

        if (isPremiumUnlocked) {
            BillingButton(
                label = stringResource(R.string.btn_already_purchased),
                onClick = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = colors.onPrimary.copy(alpha = 0.38f),
                        modifier = Modifier.size(18.dp),
                    )
                },
            )
        } else {
            BillingButtonPremium(
                label = stringResource(R.string.btn_buy_for_price, price),
                onClick = onBuyClick,
                isLoading = isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
