package com.example.firebaseapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebaseapp.ui.theme.FirebaseAppTheme

import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()

        val inputName = findViewById<EditText>(R.id.inputName)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnLoad = findViewById<Button>(R.id.btnLoad)
        val output = findViewById<TextView>(R.id.output)

        // Guardar en Firestore
        btnSave.setOnClickListener {
            val name = inputName.text.toString()
            if (name.isNotEmpty()) {
                val user = hashMapOf("name" to name)

                db.collection("users").add(user)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Guardado con éxito", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Leer desde Firestore
        btnLoad.setOnClickListener {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    val names = StringBuilder()
                    for (doc in result) {
                        val name = doc.getString("name")
                        names.append(name).append("\n")
                    }
                    output.text = names.toString()
                }
                .addOnFailureListener {
                    output.text = "Error al leer datos: ${it.message}"
                }
        }
    }
}
