# Guía de Diseño Responsivo en Android Studio

## Principios Fundamentales

1. **Adaptabilidad**: Diseña para pantallas de diferentes tamaños y resoluciones.
2. **Escalabilidad**: Usa unidades como `dp` (densidad independiente) y `sp` (para texto).
3. **Separación de responsabilidades**: Implementa MVVM o patrones similares.
4. **Fragmentos y Layouts modulares**: Facilitan la reutilización de la UI.
5. **Layouts alternativos**: Usa `layout-sw600dp`, `layout-land`, etc., para diseños adaptables.
6. **Soporte para distintas densidades**: Imágenes en mdpi, hdpi, xhdpi, etc.

---

## Herramientas y APIs Comunes

| Herramienta/API           | Uso Principal                            |
|---------------------------|-------------------------------------------|
| `ConstraintLayout`        | Posicionamiento flexible y adaptable      |
| `FlexboxLayout`           | Layout al estilo CSS flexbox             |
| `View/Data Binding`       | Acceso seguro a vistas                    |
| `Jetpack Compose`         | UI declarativa moderna                    |
| `Navigation Component`    | Navegación fluida y estructurada          |
| `Preview (Android Studio)`| Ver diseños en varios dispositivos        |

---

## Patrones Responsivos Típicos

- **Master-Detail (Teléfono vs Tablet)**
- **Bottom Navigation + Fragments**
- **Navigation Drawer + Responsive Containers**
- **Diseños de una o dos columnas según el dispositivo**

---

## Ejemplo Completo de App Responsiva de Notas

### Archivos
- `MainActivity.kt`
- `res/layout/activity_main.xml` (teléfonos)
- `res/layout-sw600dp/activity_main.xml` (tablets)

---

### `res/layout/activity_main.xml`

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/editNote"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Escribe una nota"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <Button
        android:id="@+id/btnGuardar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Guardar"
        app:layout_constraintTop_toBottomOf="@id/editNote"
        app:layout_constraintEnd_toEndOf="parent"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

---

###  `res/layout-sw600dp/activity_main.xml`

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:padding="16dp">

    <ListView
        android:id="@+id/listNotas"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="match_parent" />

    <LinearLayout
        android:layout_width="0dp"
        android:layout_weight="2"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <EditText
            android:id="@+id/editNote"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Escribe una nota" />

        <Button
            android:id="@+id/btnGuardar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Guardar" />
    </LinearLayout>
</LinearLayout>
```

---

###  `MainActivity.kt`

```kotlin
package com.example.notasresponsivas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val notas = mutableListOf<String>()
    private lateinit var adaptador: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editNote = findViewById<EditText>(R.id.editNote)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val listView = findViewById<ListView?>(R.id.listNotas)

        if (listView != null) {
            adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, notas)
            listView.adapter = adaptador
        }

        btnGuardar.setOnClickListener {
            val texto = editNote.text.toString()
            if (texto.isNotEmpty()) {
                notas.add(texto)
                if (::adaptador.isInitialized) adaptador.notifyDataSetChanged()
                editNote.text.clear()
            }
        }
    }
}
```

---

##  Bibliografía y Recursos

- [Android Developers: Responsive UI](https://developer.android.com/guide/practices/screens_support)
- [ConstraintLayout Guide](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)
- [FlexboxLayout](https://github.com/google/flexbox-layout)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design Guidelines](https://m3.material.io/)

