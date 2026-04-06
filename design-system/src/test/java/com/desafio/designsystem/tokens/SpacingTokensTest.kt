package com.desafio.designsystem.tokens

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

class SpacingTokensTest {

    @Test
    fun `BillingSpacing default values are correct`() {
        val spacing = BillingSpacing()

        assertEquals(4.dp, spacing.xs)
        assertEquals(8.dp, spacing.sm)
        assertEquals(12.dp, spacing.md)
        assertEquals(16.dp, spacing.lg)
        assertEquals(20.dp, spacing.xl)
        assertEquals(24.dp, spacing.xl2)
        assertEquals(32.dp, spacing.xl3)
    }
}
