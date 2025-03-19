package com.example.appcalculadora.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.runtime.livedata.observeAsState

@Composable
fun HistoryScreen(navController: NavController, viewModel: CalculadoraViewModel) {

    val history by viewModel.history.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(text="Historial de cálculos", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        history.forEach{
            calculation ->
            Text(text=calculation, style = MaterialTheme.typography.bodyLarge)
            Divider()
        }

        Text(text = "Historial de cálculos", fontSize = 24.sp)
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
