package com.desafio.billingapp.viewmodel

import com.desafio.billing.BillingConnectionState
import com.desafio.billingapp.R
import com.desafio.billingapp.data.repository.PurchaseRepository
import com.desafio.billingapp.utils.StringResourceProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val repository = mockk<PurchaseRepository>(relaxed = true)
    private val strings = mockk<StringResourceProvider>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { repository.connectionState } returns MutableStateFlow(BillingConnectionState.DISCONNECTED)
        every { repository.purchaseEvents } returns emptyFlow()
        every { repository.productDetails } returns MutableStateFlow(emptyList())
        every { repository.purchaseHistory } returns emptyFlow()
        every { repository.isPremiumUnlocked } returns emptyFlow()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `purchase with null product emits unavailable event`() = runTest {
        every { strings.getString(R.string.event_product_unavailable) } returns "Produto indisponível"

        val viewModel = ProductViewModel(repository, strings)

        viewModel.purchase(mockk(), null)
        testDispatcher.scheduler.advanceUntilIdle()

        val event = viewModel.events.first()
        assertEquals("Produto indisponível", event)
    }
}
