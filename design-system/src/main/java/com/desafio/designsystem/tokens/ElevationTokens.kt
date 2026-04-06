package com.desafio.designsystem.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BillingElevation(
    val card: Dp = 3.dp,
    val cardHigh: Dp = 6.dp,
)

val LocalBillingElevation = staticCompositionLocalOf { BillingElevation() }
