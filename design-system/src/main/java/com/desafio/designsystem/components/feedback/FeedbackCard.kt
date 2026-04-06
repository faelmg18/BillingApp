package com.desafio.designsystem.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun BillingErrorCard(
    message: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    dismissLabel: String = "OK",
    onDismiss: (() -> Unit)? = null,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes
    val spacing = BillingThemeTokens.spacing
    val typography = BillingThemeTokens.typography

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shapes.card)
            .background(colors.errorContainer)
            .border(1.dp, colors.error.copy(alpha = 0.4f), shapes.card)
            .padding(spacing.md),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = colors.onErrorContainer,
            modifier = Modifier.size(20.dp),
        )

        Column(modifier = Modifier.weight(1f)) {
            if (title != null) {
                Text(
                    text = title,
                    style = typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.onErrorContainer,
                )
            }
            Text(
                text = message,
                style = typography.bodyMedium,
                color = colors.onErrorContainer,
            )
        }

        if (onDismiss != null) {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissLabel,
                    color = colors.onErrorContainer,
                    fontWeight = FontWeight.Bold,
                    style = typography.labelLarge,
                )
            }
        }
    }
}
