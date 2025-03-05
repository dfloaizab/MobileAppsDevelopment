package com.example.appcalculadora

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appcalculadora.ui.theme.AppCalculadoraTheme

import android.widget.EditText
import android.widget.TextView
import android.widget.Button


class MainActivity : ComponentActivity() {


    //declarar atributos para los elementos de UI a los que vamos a acceder:
    private lateinit var txtNum1: EditText
    private lateinit var txtNum2: EditText
    private lateinit var txtResult: TextView
    private lateinit var btnSumar: Button
    private lateinit var btnRestar: Button
    private lateinit var btnMultiplicar: Button
    private lateinit var btnDividir: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //conectamos este archivo de actividad con su respectivo layout:
        setContentView(R.layout.activity_main)

        //obtener id de elementos desde el layout
        txtNum1 = findViewById(R.id.num1)
        txtNum2 = findViewById(R.id.num2)
        txtResult = findViewById(R.id.txtResultado)
        btnSumar = findViewById(R.id.btnSumar)
        btnRestar = findViewById(R.id.btnRestar)
        btnMultiplicar = findViewById(R.id.btnMultiplicar)
        btnDividir = findViewById(R.id.btnDividir)

        //adicionar listeners a los botones:
        btnSumar.setOnClickListener { calcular("+") }
        btnRestar.setOnClickListener { calcular("-") }
        btnMultiplicar.setOnClickListener { calcular("*") }
        btnDividir.setOnClickListener { calcular("/") }

        /*
        enableEdgeToEdge()
        setContent {
            AppCalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

         */
    }

    private fun calcular(operacion: String) {
        val strNum1 = txtNum1.text.toString()
        val strNum2 = txtNum2.text.toString()

        if(strNum1.isNotEmpty() && strNum2.isNotEmpty())
        {
            val num1 = strNum1.toDouble()
            val num2 = strNum2.toDouble()

            var result = 0.0

            when(operacion)
            {
                "+" -> result = num1 + num2
                "-" -> result = num1 - num2
                "*" -> result = num1 * num2
                "/" -> if(num2 != 0.0)
                        result = num1 / num2
            }
            txtResult.text = "Resultado: $result"
        }
        else
            txtResult.text = "Debe ingresar ambos operandos"

    }

    /*
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        AppCalculadoraTheme {
            Greeting("Android")
        }
    }

     */
}
