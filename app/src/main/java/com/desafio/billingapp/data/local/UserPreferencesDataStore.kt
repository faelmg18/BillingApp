package com.desafio.billingapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesDataStore(private val context: Context) {
    companion object {
        val KEY_PREMIUM_UNLOCKED = booleanPreferencesKey("premium_unlocked")
        val KEY_LAST_PRODUCT_PURCHASED = stringPreferencesKey("last_product_purchased")
    }

    val isPremiumUnlocked: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[KEY_PREMIUM_UNLOCKED] ?: false }

    val lastProductPurchased: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[KEY_LAST_PRODUCT_PURCHASED] }

    suspend fun setPremiumUnlocked(unlocked: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_PREMIUM_UNLOCKED] = unlocked
        }
    }

    suspend fun setLastProductPurchased(productId: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_LAST_PRODUCT_PURCHASED] = productId
        }
    }
}
