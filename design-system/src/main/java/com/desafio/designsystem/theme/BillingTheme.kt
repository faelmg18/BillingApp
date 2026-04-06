package com.desafio.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.desafio.designsystem.tokens.BillingColors
import com.desafio.designsystem.tokens.BillingElevation
import com.desafio.designsystem.tokens.BillingShapes
import com.desafio.designsystem.tokens.BillingSpacing
import com.desafio.designsystem.tokens.BillingTypography
import com.desafio.designsystem.tokens.ColorTokens
import com.desafio.designsystem.tokens.LightBillingColors
import com.desafio.designsystem.tokens.LocalBillingColors
import com.desafio.designsystem.tokens.LocalBillingElevation
import com.desafio.designsystem.tokens.LocalBillingShapes
import com.desafio.designsystem.tokens.LocalBillingSpacing

private val BillingColorScheme = lightColorScheme(
    primary = ColorTokens.Purple500,
    onPrimary = ColorTokens.White,
    primaryContainer = ColorTokens.Purple100,
    onPrimaryContainer = ColorTokens.Purple900,
    secondary = Color(0xFF625B71),
    onSecondary = ColorTokens.White,
    secondaryContainer = Color(0xFFDFDBE9),
    onSecondaryContainer = Color(0xFF131219),
    tertiary = Color(0xFF7D5260),
    onTertiary = ColorTokens.White,
    background = ColorTokens.White,
    onBackground = Color(0xFF212121),
    surface = ColorTokens.White,
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFDFDBE9),
    onSurfaceVariant = Color(0xFF3A3645),
    error = ColorTokens.Red500,
    onError = ColorTokens.White,
    errorContainer = ColorTokens.Red50,
    onErrorContainer = ColorTokens.Red900,
    outline = Color(0xFFBDBDBD),
    outlineVariant = Color(0xFFE0E0E0),
    scrim = Color(0xFF000000),
)

@Composable
fun BillingTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalBillingColors provides LightBillingColors,
        LocalBillingSpacing provides BillingSpacing(),
        LocalBillingShapes provides BillingShapes(),
        LocalBillingElevation provides BillingElevation(),
    ) {
        MaterialTheme(
            colorScheme = BillingColorScheme,
            typography = BillingTypography,
            content = content,
        )
    }
}

object BillingThemeTokens {
    val colors: BillingColors
        @Composable @ReadOnlyComposable get() = LocalBillingColors.current

    val spacing: BillingSpacing
        @Composable @ReadOnlyComposable get() = LocalBillingSpacing.current

    val shapes: BillingShapes
        @Composable @ReadOnlyComposable get() = LocalBillingShapes.current

    val elevation: BillingElevation
        @Composable @ReadOnlyComposable get() = LocalBillingElevation.current

    val typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography
}
