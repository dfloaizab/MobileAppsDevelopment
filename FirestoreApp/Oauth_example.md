# Aplicaciones Móviles, 2025A
# Universidad Libre, Cali
## Autenticación con Google (OAuth) en Android usando Firebase

Este documento le guía paso a paso para implementar autenticación con Google en una app Android utilizando Firebase Authentication.

## Requisitos previos

- Android Studio instalado
- Cuenta de Firebase y un proyecto creado en [https://console.firebase.google.com](https://console.firebase.google.com)
- Conocimientos básicos de Kotlin

---

## Paso 1: Configurar Firebase y Google Sign-In

1. Vaya a su [consola de Firebase](https://console.firebase.google.com)
2. Crea un nuevo proyecto (si no tiene uno)
3. En el menú lateral, vaya a **Authentication > Métodos de inicio de sesión**
4. Active **Google** y guarde los cambios
5. En **Project settings > General**, añada su app Android con:
   - Nombre del paquete (ej. `com.ejemplo.googleauth`)
   - SHA-1: obtenido desde Android Studio (Gradle > signingReport)
6. Descargue el archivo `google-services.json` y colóquelo en `/app`

---

## Paso 2: Configurar Gradle

### `build.gradle (Project)`

```gradle
classpath 'com.google.gms:google-services:4.3.15'
```

### `build.gradle (Module: app)`

```gradle
plugins {
    id 'com.android.application'
    id 'com.google.gms.google-services'
}

dependencies {
    implementation 'com.google.firebase:firebase-auth-ktx:22.1.2'
    implementation 'com.google.android.gms:play-services-auth:20.7.0'
}
```

---

## Paso 3: Crear la interfaz

### `res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:padding="24dp">

    <com.google.android.gms.common.SignInButton
        android:id="@+id/sign_in_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</LinearLayout>
```

---

## Paso 4: Implementar Google Sign-In

### `MainActivity.kt`

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("MainActivity", "Google sign in failed", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signInLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Falló la autenticación", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
```

---

## Resultado

Al ejecutar la app, el usuario verá un botón de "Iniciar sesión con Google". Al autenticarse, se mostrará un mensaje de bienvenida.

---

## Referencias

- [Documentación oficial Firebase Authentication](https://firebase.google.com/docs/auth/android/google-signin)
- [GoogleSignInOptions en Android](https://developers.google.com/identity/sign-in/android/start-integrating)
