package com.desafio.billingapp

import android.app.Application
import com.desafio.billing.billingModule
import com.desafio.billingapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BillingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BillingApp)
            modules(billingModule, appModule)
        }
    }
}
