package com.desafio.billingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.desafio.billingapp.ui.screens.ProductScreen
import com.desafio.billingapp.ui.theme.BillingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BillingAppTheme {
                ProductScreen()
            }
        }
    }
}
