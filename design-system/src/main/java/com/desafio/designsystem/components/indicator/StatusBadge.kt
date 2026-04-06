package com.desafio.designsystem.components.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desafio.designsystem.R
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun StatusDot(
    color: Color,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 10.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(BillingThemeTokens.shapes.full)
            .background(color),
    )
}

@Composable
fun BillingStatusChip(
    label: String,
    dotColor: Color,
    modifier: Modifier = Modifier,
    background: Color = dotColor.copy(alpha = 0.15f),
    textColor: Color = dotColor,
) {
    val shapes = BillingThemeTokens.shapes
    val spacing = BillingThemeTokens.spacing

    Row(
        modifier = modifier
            .clip(shapes.chip)
            .background(background)
            .padding(horizontal = spacing.sm, vertical = spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.xs),
    ) {
        StatusDot(color = dotColor, size = 8.dp)
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun BillingPremiumBadge(modifier: Modifier = Modifier) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes
    val spacing = BillingThemeTokens.spacing

    Box(
        modifier = modifier
            .clip(shapes.chip)
            .background(colors.premiumGradient)
            .padding(horizontal = spacing.sm, vertical = spacing.xs),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.premium),
            color = colors.onPremium,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
        )
    }
}
