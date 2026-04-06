package com.desafio.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.theme.BillingThemeTokens

enum class BillingButtonSize { Small, Medium, Large }

private val BillingButtonSize.contentPadding: PaddingValues
    get() = when (this) {
        BillingButtonSize.Small -> PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        BillingButtonSize.Medium -> PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        BillingButtonSize.Large -> PaddingValues(horizontal = 28.dp, vertical = 16.dp)
    }

@Composable
fun BillingButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    size: BillingButtonSize = BillingButtonSize.Medium,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        shape = shapes.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary,
            contentColor = colors.onPrimary,
            disabledContainerColor = colors.primary.copy(alpha = 0.38f),
            disabledContentColor = colors.onPrimary.copy(alpha = 0.38f),
        ),
        contentPadding = size.contentPadding,
    ) {
        BillingButtonContent(label, isLoading, leadingIcon)
    }
}

@Composable
fun BillingButtonOutlined(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    size: BillingButtonSize = BillingButtonSize.Medium,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        shape = shapes.button,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colors.primary,
            disabledContentColor = colors.primary.copy(alpha = 0.38f),
        ),
        contentPadding = size.contentPadding,
    ) {
        BillingButtonContent(label, isLoading, leadingIcon)
    }
}

@Composable
fun BillingButtonPremium(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val colors = BillingThemeTokens.colors
    val shapes = BillingThemeTokens.shapes
    val spacing = BillingThemeTokens.spacing

    Box(
        modifier = modifier
            .clip(shapes.button)
            .background(
                brush = if (enabled) colors.premiumGradient else colors.premiumGradient,
                alpha = if (enabled) 1f else 0.38f,
                shape = shapes.button,
            )
            .then(
                Modifier
                    .defaultMinSize(minHeight = 48.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        val contentColor = if (enabled) colors.onPremium else colors.onPremium.copy(alpha = 0.38f)
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Row(
                modifier = Modifier.padding(horizontal = spacing.xl, vertical = spacing.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                BillingButtonContent(label, isLoading, leadingIcon, tintColor = contentColor)
            }
        }

        Button(
            onClick = onClick,
            modifier = Modifier.matchParentSize(),
            enabled = enabled && !isLoading,
            shape = shapes.button,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp),
            contentPadding = PaddingValues(0.dp),
        ) {}
    }
}

@Composable
private fun BillingButtonContent(
    label: String,
    isLoading: Boolean,
    leadingIcon: @Composable (() -> Unit)?,
    tintColor: Color = Color.Unspecified,
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = if (tintColor != Color.Unspecified) tintColor else LocalContentColor.current,
            strokeWidth = 2.dp,
        )
        Spacer(Modifier.width(8.dp))
    } else {
        leadingIcon?.invoke()
        if (leadingIcon != null) Spacer(Modifier.width(8.dp))
    }
    Text(label, fontWeight = FontWeight.Bold)
}
