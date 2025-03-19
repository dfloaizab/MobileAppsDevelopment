package com.example.appcalculadora.main


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    val viewModel = remember { CalculadoraViewModel() }

    NavHost(navController = navController, startDestination = "mainScreen") {
        composable("mainScreen") { CalculatorScreen(navController, viewModel) }
        composable("history") { HistoryScreen(navController, viewModel) }
    }
}
