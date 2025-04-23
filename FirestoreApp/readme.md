
# Aplicaciones Móviles, 2025A
# Universidad Libre, Cali
# App Android con Firebase Firestore + Autenticación

Paso a paso para crear Una aplicación Android básica para registrar e iniciar sesión con correo y contraseña usando **Firebase Authentication**, y guardar/leer información del usuario (nombre, edad y ciudad) usando **Firestore Database**.

---

## Requisitos

- Android Studio (Ladybug o superior)
- Una cuenta en [Firebase](https://console.firebase.google.com/) con su aplicación Android configurada
- Dispositivo físico o emulador Android (API 21+)

---

## Paso 1: Crear proyecto Android

1. Abra Android Studio > `New Project` > selecciona **Empty Activity**
2. Nombre del proyecto: `FirestoreAuthApp`
3. Lenguaje: **Kotlin**
4. Mínimo SDK: API 21 (Lollipop)

---

## Paso 2: Conectar Firebase

1. En Android Studio, abra el panel `Tools > Firebase`
2. En `Authentication`, seleccione **Email and Password Authentication** y siga las instrucciones
3. Luego en `Cloud Firestore`, seleccione **Save and retrieve data** y siga los pasos

Esto añadirá automáticamente los plugins y dependencias necesarias en su proyecto.

---

## Paso 3: Añadir dependencias manualmente (si es necesario)

En `build.gradle (Module)` agregue:

```gradle
dependencies {
    implementation 'com.google.firebase:firebase-auth-ktx'
    implementation 'com.google.firebase:firebase-firestore-ktx'
}
```

Y en `build.gradle (Project)` asegúrese de tener:

```gradle
classpath 'com.google.gms:google-services:4.3.15'
```

En el archivo `build.gradle (Module)` al final:

```gradle
apply plugin: 'com.google.gms.google-services'
```

---

## Paso 4: Diseñar la interfaz XML

Reemplace el contenido de `res/layout/activity_main.xml` con:

```xml
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent">

    <LinearLayout
        android:orientation="vertical" android:padding="16dp"
        android:layout_width="match_parent" android:layout_height="wrap_content">

        <!-- Autenticación -->
        <EditText android:id="@+id/emailInput" android:hint="Correo electrónico"
            android:inputType="textEmailAddress"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <EditText android:id="@+id/passwordInput" android:hint="Contraseña"
            android:inputType="textPassword"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <Button android:id="@+id/btnLogin" android:text="Iniciar sesión / Registrar"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <!-- Formulario de datos -->
        <EditText android:id="@+id/inputName" android:hint="Nombre"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <EditText android:id="@+id/inputAge" android:hint="Edad" android:inputType="number"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <EditText android:id="@+id/inputCity" android:hint="Ciudad"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <Button android:id="@+id/btnSave" android:text="Guardar en Firestore"
            android:layout_width="match_parent" android:layout_height="wrap_content" />

        <TextView android:id="@+id/outputText" android:text="Resultados..."
            android:layout_width="match_parent" android:layout_height="wrap_content"
            android:paddingTop="16dp" />
    </LinearLayout>
</ScrollView>
```

---

## Paso 5: Código Kotlin de la lógica

Copia en `MainActivity.kt`:

```kotlin
// Código Kotlin completo aquí (ver conversación anterior)
```

---

## Paso 6: Reglas de Firestore

Vaya a la consola de Firebase > Firestore > Reglas:

```js
service cloud.firestore {
  match /databases/{database}/documents {
    match /usuarios/{document} {
      allow read, write: if request.auth != null;
    }
  }
}
```

---

## Paso 7: pruebe su app

1. Ejecute la app en un emulador o dispositivo
2. Escriba un correo y contraseña > presione **Iniciar sesión / Registrar**
3. Rellene el formulario y guarde los datos
4. Verá los datos de todos los usuarios autenticados en el `TextView`

---

## 🔒 Autenticación en Firebase

En su proyecto Firebase:

1. Vaya a `Authentication > Métodos de inicio de sesión`
2. Habilite **Email/Password**

---

## Referencias

- [Firebase para Android](https://firebase.google.com/docs/android/setup)
- [Firestore Android](https://firebase.google.com/docs/firestore/quickstart)
- [Firebase Auth](https://firebase.google.com/docs/auth/android/start)
