package com.desafio.designsystem.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.theme.BillingTheme
import com.desafio.designsystem.theme.BillingThemeTokens

/**
 * Circular avatar with an optional ring border — used on Instagram-style profile screens
 * and story highlights.
 *
 * @param size        Outer diameter of the component (includes ring + padding).
 * @param ringWidth   Stroke width of the outer ring. Pass 0.dp to hide the ring.
 * @param ringColor   Color of the outer ring. Defaults to [InstagramColors.border].
 * @param innerPadding Gap between ring and inner content circle.
 * @param content     Composable placed inside the clipped circle (image, icon, etc.).
 */
@Composable
fun AvatarCircle(
    size: Dp,
    modifier: Modifier = Modifier,
    ringWidth: Dp = 1.5.dp,
    ringColor: Color = BillingThemeTokens.instagramColors.border,
    innerPadding: Dp = 3.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .then(
                if (ringWidth > 0.dp) Modifier.border(ringWidth, ringColor, CircleShape)
                else Modifier
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(size - (ringWidth + innerPadding) * 2)
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
            content = content,
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun AvatarCirclePreview() {
    BillingTheme {
        AvatarCircle(size = 96.dp) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFFCCCCCC)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}
