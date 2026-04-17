package com.desafio.billingapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.desafio.designsystem.components.button.BillingButton
import com.desafio.designsystem.components.button.BillingButtonOutlined
import com.desafio.designsystem.components.layout.BillingScaffold
import com.desafio.designsystem.theme.BillingTheme
import com.desafio.designsystem.theme.BillingThemeTokens

@Composable
fun SampleScreen(
    onNavigateToBilling: () -> Unit = {},
    onNavigateToInstagram: () -> Unit = {},
) {
    val spacing        = BillingThemeTokens.spacing
    val typography     = BillingThemeTokens.typography
    val colors         = BillingThemeTokens.colors
    val snackbarState  = remember { SnackbarHostState() }

    BillingScaffold(
        title = "Samples",
        snackbarHostState = snackbarState,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = spacing.lg),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "🚀 Escolha uma tela",
                style = typography.headlineSmall,
                color = colors.onSurface,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(spacing.sm))

            Text(
                text = "Selecione abaixo qual feature deseja visualizar.",
                style = typography.bodyMedium,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(spacing.xl3))

            // Botão Billing
            BillingButton(
                label = "Billing Screen",
                onClick = onNavigateToBilling,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                    )
                },
            )

            Spacer(Modifier.height(spacing.lg))

            // Botão Instagram Profile
            BillingButtonOutlined(
                label = "Instagram Profile",
                onClick = onNavigateToInstagram,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SampleScreenPreview() {
    BillingTheme {
        SampleScreen()
    }
}
