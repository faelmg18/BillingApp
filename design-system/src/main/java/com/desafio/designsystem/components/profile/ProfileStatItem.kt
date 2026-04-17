package com.desafio.designsystem.components.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.desafio.designsystem.theme.BillingTheme
import com.desafio.designsystem.theme.BillingThemeTokens

/**
 * A count + label pair used in the Instagram-style profile stats row.
 *
 * Example:
 * ```
 *   54        834        162
 *  Posts   Followers  Following
 * ```
 */
@Composable
fun ProfileStatItem(
    count: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    val igColors = BillingThemeTokens.instagramColors
    val typography = BillingThemeTokens.typography

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = count,
            style = typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = igColors.textPrimary,
        )
        Text(
            text = label,
            style = typography.bodySmall,
            color = igColors.textPrimary,
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun ProfileStatItemPreview() {
    BillingTheme {
        ProfileStatItem(count = "834", label = "Followers")
    }
}
