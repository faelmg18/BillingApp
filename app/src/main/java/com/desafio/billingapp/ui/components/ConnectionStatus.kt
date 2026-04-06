package com.desafio.billingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.desafio.billing.BillingConnectionState
import com.desafio.billingapp.R
import com.desafio.designsystem.components.indicator.BillingStatusChip
import com.desafio.designsystem.theme.BillingThemeTokens

private val BillingConnectionState.dotColor: Color
    @Composable get() {
        val colors = BillingThemeTokens.colors
        return when (this) {
            BillingConnectionState.CONNECTED -> colors.success
            BillingConnectionState.CONNECTING -> colors.warning
            BillingConnectionState.DISCONNECTED -> colors.error
            BillingConnectionState.CLOSED -> colors.onSurfaceVariant
        }
    }

private val BillingConnectionState.label: String
    @Composable get() = when (this) {
        BillingConnectionState.CONNECTED -> stringResource(R.string.connection_connected)
        BillingConnectionState.CONNECTING -> stringResource(R.string.connection_connecting)
        BillingConnectionState.DISCONNECTED -> stringResource(R.string.connection_disconnected)
        BillingConnectionState.CLOSED -> stringResource(R.string.connection_closed)
    }

@Composable
fun ConnectionStatusBar(
    state: BillingConnectionState,
    modifier: Modifier = Modifier,
) {
    val spacing = BillingThemeTokens.spacing

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        BillingStatusChip(
            label = state.label,
            dotColor = state.dotColor,
        )
    }
}
