package com.desafio.billingapp.utils

import android.content.Context
import androidx.annotation.StringRes

interface StringResourceProvider {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg args: Any): String
}

class AndroidStringResourceProvider(private val context: Context) : StringResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getString(resId: Int, vararg args: Any): String = context.getString(resId, *args)
}
