Para incorporar el login con SharedPreferences a una aplicación existente que ya tenga una calculadora sencilla, debe seguir estos pasos:

## 1. Estructura del flujo de la aplicación

Primero, necesita establecer el flujo de la aplicación. La idea es que:
- El LoginActivity sea la primera pantalla mostrada
- Solo si el login es exitoso, se muestre la CalculadoraActivity
- El usuario no pueda acceder a la calculadora sin autenticarse

## 2. Modificar el AndroidManifest.xml

Necesitará modificar el archivo `AndroidManifest.xml` para establecer el LoginActivity como actividad principal:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.ejemplo.miapp">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MiApp">
        
        <!-- Cambiar el LoginActivity a LAUNCHER -->
        <activity
            android:name=".LoginActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
        <!-- La actividad de la calculadora ya no es LAUNCHER -->
        <activity 
            android:name=".CalculadoraActivity"
            android:exported="false" />
            
    </application>
</manifest>
```

## 3. Crear el LoginActivity

Cree el nuevo archivo `LoginActivity.kt` con todo el código de login que hemos visto:

```kotlin
class LoginActivity : AppCompatActivity() {
    // Todo el código del login que vimos antes
    // ...
}
```

## 4. Modificar la navegación hacia la calculadora

En el método `login()`, cuando el inicio de sesión sea exitoso, reemplace:

```kotlin
startActivity(Intent(this, MainActivity::class.java))
```

por:

```kotlin
startActivity(Intent(this, CalculadoraActivity::class.java))
```

## 5. Añadir opción de cerrar sesión en la calculadora

En `CalculadoraActivity.kt`, añada un botón para cerrar sesión (por ejemplo, en el menú o como un botón adicional):

```kotlin
class CalculadoraActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)
        
        // Código existente de la calculadora
        // ...
        
        // Añadir opción para cerrar sesión
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logout()
        }
    }
    
    private fun logout() {
        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean("isLoggedIn", false)
            apply()
        }
        
        // Volver a la pantalla de login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
```

## 6. Actualizar el layout de la calculadora

Modifique `activity_calculadora.xml` para añadir un botón de logout:

```xml
<!-- Añadir donde corresponda en el layout existente -->
<Button
    android:id="@+id/btnLogout"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Cerrar Sesión" />
```

## 7. Verificar autenticación en la calculadora

Para mayor seguridad, también puede verificar en `CalculadoraActivity` si el usuario está autenticado:

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // Verificar autenticación
    val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
    if (!sharedPreferences.getBoolean("isLoggedIn", false)) {
        // Si no está autenticado, volver al login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        return
    }
    
    setContentView(R.layout.activity_calculadora)
    // Resto del código...
}
```

## 8. Opciones adicionales (opcionales)

- **Recordar usuario**: Añada un checkbox "Recordar usuario" en el login para rellenar automáticamente el campo de usuario la próxima vez.
- **Tiempo de sesión**: Implemente una expiración de sesión guardando un timestamp.
- **Personalización**: Muestre el nombre del usuario actual en la calculadora con un mensaje de bienvenida.

## Flujo completo de la aplicación

1. La app se inicia y muestra LoginActivity
2. Si el usuario ya inició sesión previamente (isLoggedIn = true), pasa directamente a CalculadoraActivity
3. Si no, el usuario debe introducir credenciales o registrarse
4. Tras un login exitoso, se muestra CalculadoraActivity
5. El usuario puede usar la calculadora y luego cerrar sesión cuando lo desee
6. Al cerrar sesión, vuelve a LoginActivity

Esta integración mantiene la funcionalidad de la calculadora intacta mientras añade una capa de autenticación de usuarios sencilla.
