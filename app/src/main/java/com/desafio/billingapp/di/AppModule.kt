package com.desafio.billingapp.di

import androidx.room.Room
import com.desafio.billingapp.data.local.AppDatabase
import com.desafio.billingapp.data.local.SecurePreferences
import com.desafio.billingapp.data.local.UserPreferencesDataStore
import com.desafio.billingapp.data.repository.PurchaseRepository
import com.desafio.billingapp.utils.AndroidStringResourceProvider
import com.desafio.billingapp.utils.StringResourceProvider
import com.desafio.billingapp.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "billing_database").build()
    }
    single { get<AppDatabase>().purchaseDao() }
    single { UserPreferencesDataStore(androidContext()) }
    single { SecurePreferences(androidContext()) }
    single { PurchaseRepository(get(), get(), get(), get()) }
    single<StringResourceProvider> { AndroidStringResourceProvider(androidContext()) }
    viewModel { ProductViewModel(get(), get()) }
}
