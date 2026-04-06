package com.desafio.designsystem.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun BillingCardSurface(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shapes.card,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surfaceVariant,
            contentColor = colors.onSurfaceVariant,
        ),
    ) {
        Column(content = content)
    }
}

@Composable
fun BillingCardPremium(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes
    val spacing = BillingThemeTokens.spacing

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shapes.card,
        elevation = CardDefaults.cardElevation(defaultElevation = BillingThemeTokens.elevation.cardHigh),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface,
            contentColor = colors.onSurface,
        ),
        border = BorderStroke(2.dp, colors.premium),
    ) {
        Column(
            modifier = Modifier.padding(spacing.lg),
            content = content,
        )
    }
}
