package com.desafio.designsystem.tokens

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class BillingShapes(
    val small: Shape = RoundedCornerShape(8.dp),
    val full: Shape = CircleShape,
    val button: Shape = RoundedCornerShape(50.dp),
    val card: Shape = RoundedCornerShape(16.dp),
    val chip: Shape = RoundedCornerShape(8.dp),
    val heroBanner: Shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
)

val LocalBillingShapes = staticCompositionLocalOf { BillingShapes() }
