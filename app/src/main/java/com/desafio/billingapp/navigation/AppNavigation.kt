package com.desafio.billingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.desafio.billingapp.ui.screens.ProductScreen
import com.desafio.billingapp.ui.screens.SampleScreen
import com.desafio.instagram_test.InstagramProfileScreen

object Routes {
    const val SAMPLE   = "sample"
    const val BILLING  = "billing"
    const val INSTAGRAM = "instagram"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SAMPLE,
    ) {
        composable(Routes.SAMPLE) {
            SampleScreen(
                onNavigateToBilling   = { navController.navigate(Routes.BILLING) },
                onNavigateToInstagram = { navController.navigate(Routes.INSTAGRAM) },
            )
        }
        composable(Routes.BILLING) {
            ProductScreen()
        }
        composable(Routes.INSTAGRAM) {
            InstagramProfileScreen()
        }
    }
}
