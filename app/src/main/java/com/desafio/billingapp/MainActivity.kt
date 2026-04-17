package com.desafio.billingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.desafio.billingapp.navigation.AppNavigation
import com.desafio.designsystem.theme.BillingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BillingTheme {
                AppNavigation()
            }
        }
    }
}
