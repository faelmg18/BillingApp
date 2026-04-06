package com.desafio.billingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.desafio.billingapp.R
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun HeroBanner(
    isPremium: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes
    val spacing = BillingThemeTokens.spacing
    val typography = BillingThemeTokens.typography

    val gradient = if (isPremium) colors.successGradient else colors.primaryGradientVertical

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shapes.heroBanner)
            .background(gradient)
            .padding(horizontal = spacing.xl2, vertical = spacing.xl3),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        Icon(
            imageVector = if (isPremium) Icons.Default.CheckCircle else Icons.Default.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(52.dp),
        )

        Text(
            text = stringResource(if (isPremium) R.string.hero_title_premium else R.string.hero_title_default),
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
        )

        Text(
            text = stringResource(if (isPremium) R.string.hero_subtitle_premium else R.string.hero_subtitle_default),
            style = typography.bodyMedium,
            color = Color.White.copy(alpha = 0.88f),
            textAlign = TextAlign.Center,
        )
    }
}
