package com.example.appcalculadora.main

import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.appcalculadora.MainActivity
import com.example.appcalculadora.R

class LoginActivity<Bundle> : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREF_NAME = "LoginPrefs"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        // Comprobar si el usuario ya ha iniciado sesión
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Si ya está logueado, ir directamente a la pantalla principal
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Inicializar vistas
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        // Configurar listeners
        btnLogin.setOnClickListener {
            login()
        }

        btnRegister.setOnClickListener {
            register()
        }
    }

    // 2. Método para iniciar sesión
    private fun login() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Comprobar credenciales
        val savedPassword = sharedPreferences.getString(username, "") ?: ""

        if (savedPassword == password) {
            // Guardar estado de sesión
            sharedPreferences.edit().apply {
                putBoolean("isLoggedIn", true)
                putString("currentUser", username)
                apply()
            }

            // Ir a la actividad principal
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Método para registrar un nuevo usuario
    private fun register() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Comprobar si el usuario ya existe
        if (sharedPreferences.contains(username)) {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar nuevo usuario
        sharedPreferences.edit().apply {
            putString(username, password)
            apply()
        }

        Toast.makeText(this, "Registro exitoso, ahora puedes iniciar sesión", Toast.LENGTH_SHORT).show()
    }
}

// 4. Cerrar sesión (para incluir en MainActivity.kt)
fun logout() {
    val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
    sharedPreferences.edit().apply {
        clear()
        // O si prefieres mantener algunos datos:
        // remove("isLoggedIn")
        // remove("currentUser")
        apply()
    }

    startActivity(Intent(this, LoginActivity::class.java))
    finish()
}
