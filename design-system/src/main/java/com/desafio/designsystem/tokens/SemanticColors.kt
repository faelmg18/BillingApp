package com.desafio.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class BillingColors(

    val primary: Color,
    val primaryLight: Color,
    val primaryDark: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,

    val premium: Color,
    val premiumLight: Color,
    val premiumDark: Color,
    val onPremium: Color,

    val success: Color,

    val error: Color,
    val errorContainer: Color,
    val onError: Color,
    val onErrorContainer: Color,

    val warning: Color,

    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,

    val outline: Color,
    val outlineVariant: Color,
) {
    val premiumGradient: Brush
        get() = Brush.linearGradient(listOf(premiumLight, premiumDark))

    val primaryGradientVertical: Brush
        get() = Brush.verticalGradient(listOf(primaryLight, primaryDark))

    val successGradient: Brush
        get() = Brush.linearGradient(listOf(success, Color(0xFF2E7D32)))
}

val LightBillingColors = BillingColors(
    primary = ColorTokens.Purple500,
    primaryLight = ColorTokens.Purple400,
    primaryDark = ColorTokens.Purple700,
    onPrimary = ColorTokens.White,
    primaryContainer = ColorTokens.Purple100,
    onPrimaryContainer = ColorTokens.Purple900,

    premium = ColorTokens.Blue400,
    premiumLight = ColorTokens.Violet400,
    premiumDark = ColorTokens.Violet700,
    onPremium = ColorTokens.White,

    success = ColorTokens.Green500,

    error = ColorTokens.Red500,
    errorContainer = ColorTokens.Red50,
    onError = ColorTokens.White,
    onErrorContainer = ColorTokens.Red900,

    warning = ColorTokens.Orange500,

    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),
    surfaceContainer = Color(0xFFF3EDF7),
    surfaceContainerHigh = Color(0xFFECE6F0),

    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC4D0),
)

val LocalBillingColors = staticCompositionLocalOf { LightBillingColors }
