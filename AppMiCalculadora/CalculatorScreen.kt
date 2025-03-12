package com.example.appcalculadora

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CalculatorScreen(navController: NavController) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Calculadora", style = MaterialTheme.typography.headlineLarge)
        
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Número 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Número 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Button(onClick = { result = calcular(num1, num2, "+") }) { Text("+") }
            Button(onClick = { result = calcular(num1, num2, "-") }) { Text("-") }
            Button(onClick = { result = calcular(num1, num2, "*") }) { Text("*") }
            Button(onClick = { result = calcular(num1, num2, "/") }) { Text("/") }
        }

        Text(text = "Resultado: $result", style = MaterialTheme.typography.bodyLarge)

        Button(
            onClick = { navController.navigate("history") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Ver Historial")
        }
    }
}

fun calcular(n1: String, n2: String, operacion: String): String {
    val num1 = n1.toDoubleOrNull()
    val num2 = n2.toDoubleOrNull()

    if (num1 == null || num2 == null) return "Error: Ingrese números válidos"

    return when (operacion) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "*" -> (num1 * num2).toString()
        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error: División por cero"
        else -> "Operación no válida"
    }
}
