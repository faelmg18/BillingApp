package com.desafio.billing

import org.junit.Assert.assertEquals
import org.junit.Test

class PurchaseResultTest {

    @Test
    fun `Error stores responseCode and message correctly`() {
        val result = PurchaseResult.Error(responseCode = 3, message = "User cancelled")

        assertEquals(3, result.responseCode)
        assertEquals("User cancelled", result.message)
    }
}
