package com.desafio.designsystem.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.theme.BillingTheme
import com.desafio.designsystem.theme.BillingThemeTokens

/**
 * Story / highlight circle as seen on Instagram-style profile screens.
 *
 * @param name      Label displayed below the circle.
 * @param isNew     When true, renders an "Add" icon instead of content (the "New" story slot).
 * @param size      Diameter of the outer ring circle.
 * @param onClick   Click callback.
 * @param content   Optional inner content (profile image, cover, etc.).
 *                  Ignored when [isNew] is true.
 */
@Composable
fun StoryHighlight(
    name: String,
    modifier: Modifier = Modifier,
    isNew: Boolean = false,
    size: Dp = 64.dp,
    onClick: () -> Unit = {},
    content: (@Composable () -> Unit)? = null,
) {
    val igColors = BillingThemeTokens.instagramColors
    val typography = BillingThemeTokens.typography
    val spacing = BillingThemeTokens.spacing

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(onClick = onClick),
    ) {
        AvatarCircle(
            size = size,
            ringWidth = 1.dp,
            ringColor = igColors.border,
            innerPadding = if (isNew) 0.dp else 4.dp,
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(igColors.placeholder),
                contentAlignment = Alignment.Center,
            ) {
                if (isNew) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar story",
                        tint = igColors.textPrimary,
                        modifier = Modifier.size(22.dp),
                    )
                } else {
                    content?.invoke()
                }
            }
        }

        Spacer(Modifier.height(spacing.xs))

        Text(
            text = name,
            style = typography.labelSmall,
            color = igColors.textPrimary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun StoryHighlightPreview() {
    BillingTheme {
        StoryHighlight(name = "Friends")
    }
}

@Preview(showBackground = true)
@Composable
private fun StoryHighlightNewPreview() {
    BillingTheme {
        StoryHighlight(name = "New", isNew = true)
    }
}
