package com.desafio.billingapp.data.local

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SecurePreferences(private val context: Context) {

    companion object {
        private const val FILE_NAME = "secure_billing_prefs"
        private const val KEY_PURCHASE_TOKEN = "purchase_token_"
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val KEYSTORE_ALIAS = "billing_app_key"
        private const val ALGORITHM = "AES/GCM/NoPadding"
        private const val KEY_SIZE = 256
        private const val GCM_TAG_LENGTH = 128
        private const val IV_SIZE = 12
    }

    private val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).also { it.load(null) }

    private val sharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    private fun getOrCreateKey(): SecretKey {
        if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            val spec = KeyGenParameterSpec.Builder(
                KEYSTORE_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(KEY_SIZE)
                .build()

            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER)
                .apply { init(spec) }
                .generateKey()
        }
        return keyStore.getKey(KEYSTORE_ALIAS, null) as SecretKey
    }

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, getOrCreateKey())
        }
        val iv = cipher.iv
        val encrypted = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encrypted, Base64.DEFAULT)
    }

    private fun decrypt(encoded: String): String {
        val combined = Base64.decode(encoded, Base64.DEFAULT)
        val iv = combined.sliceArray(0 until IV_SIZE)
        val encrypted = combined.sliceArray(IV_SIZE until combined.size)
        val cipher = Cipher.getInstance(ALGORITHM).apply {
            init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(GCM_TAG_LENGTH, iv))
        }
        return cipher.doFinal(encrypted).toString(Charsets.UTF_8)
    }

    fun savePurchaseToken(productId: String, token: String) {
        sharedPreferences.edit()
            .putString("$KEY_PURCHASE_TOKEN$productId", encrypt(token))
            .apply()
    }

    fun getPurchaseToken(productId: String): String? {
        return sharedPreferences
            .getString("$KEY_PURCHASE_TOKEN$productId", null)
            ?.let { decrypt(it) }
    }

    fun removePurchaseToken(productId: String) {
        sharedPreferences.edit()
            .remove("$KEY_PURCHASE_TOKEN$productId")
            .apply()
    }

    fun hasPurchaseToken(productId: String): Boolean {
        return sharedPreferences.contains("$KEY_PURCHASE_TOKEN$productId")
    }
}
