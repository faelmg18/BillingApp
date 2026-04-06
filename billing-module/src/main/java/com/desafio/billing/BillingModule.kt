package com.desafio.billing

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val billingModule = module {
    single<BillingManager> { BillingManagerImpl(androidContext()) }
}
