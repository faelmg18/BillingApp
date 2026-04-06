package com.desafio.designsystem.components.feedback

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun BillingSkeletonBox(
    modifier: Modifier = Modifier,
    width: Dp = Dp.Unspecified,
    height: Dp = 16.dp,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes

    val shimmerColors = listOf(
        colors.surfaceVariant,
        colors.outline.copy(alpha = 0.2f),
        colors.surfaceVariant,
    )

    val transition = rememberInfiniteTransition(label = "Shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200),
            repeatMode = RepeatMode.Restart,
        ),
        label = "ShimmerTranslate",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 200f, 0f),
        end = Offset(translateAnim, 0f),
    )

    val sizeModifier =
        if (width != Dp.Unspecified) Modifier.size(width, height) else Modifier
            .fillMaxWidth()
            .height(height)

    Box(
        modifier = modifier
            .then(sizeModifier)
            .clip(shapes.small)
            .background(brush),
    )
}

@Composable
fun BillingSkeletonCard(modifier: Modifier = Modifier) {
    val spacing = BillingThemeTokens.spacing
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shapes.card)
            .background(colors.surfaceContainer)
            .padding(spacing.lg),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(spacing.sm)) {
            listOf(
                20.dp to 160.dp,
                14.dp to Dp.Unspecified,
                14.dp to Dp.Unspecified,
                14.dp to Dp.Unspecified,
                14.dp to 120.dp
            ).forEach {
                BillingSkeletonBox(height = it.first, width = it.second)
            }
            Spacer(Modifier.height(spacing.sm))
            BillingSkeletonBox(height = 48.dp)
        }
    }
}
