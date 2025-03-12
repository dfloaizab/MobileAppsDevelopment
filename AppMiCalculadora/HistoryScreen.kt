package com.example.appcalculadora

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HistoryScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Historial de cálculos", fontSize = 24.sp)
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
