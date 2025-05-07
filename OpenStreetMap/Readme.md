# Aplicaciones Móviles, 2025A
# Universidad Libre
## Mini-aplicación Android con OpenStreetMap

Esta es una mini aplicación Android usa OpenStreetMap (OSM) como alternativa gratuita y de código abierto a Google Maps, sin necesidad de registro con tarjeta de crédito.

## Estructura del proyecto

### 1. Configuración del build.gradle (nivel del módulo)

```gradle
// build.gradle (Module: app)
dependencies {
    // Dependencias estándar de Android...
    
    // Biblioteca OSMDroid (OpenStreetMap para Android)
    implementation 'org.osmdroid:osmdroid-android:6.1.16'
    
    // Biblioteca para gestionar permisos de forma fácil (opcional)
    implementation 'pub.devrel:easypermissions:3.0.0'
}
```

### 2. Layout del mapa (activity_main.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <org.osmdroid.views.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabMyLocation"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:contentDescription="Mi ubicación"
        android:src="@android:drawable/ic_menu_mylocation"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### 3. MainActivity.kt

```kotlin
package com.example.osmappdemo

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var mapView: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay
    
    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
        private const val DEFAULT_ZOOM = 15.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configuración de OSMDroid
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = packageName
        
        setContentView(R.layout.activity_main)
        
        // Inicializar mapa
        mapView = findViewById(R.id.mapView)
        setupMap()
        
        // Solicitar permisos
        requestLocationPermission()
        
        // Configurar botón para centrar en ubicación
        val fabMyLocation = findViewById<FloatingActionButton>(R.id.fabMyLocation)
        fabMyLocation.setOnClickListener {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                centerMapOnLocation()
            } else {
                requestLocationPermission()
            }
        }
        
        // Agregar un marcador de ejemplo
        addSampleMarker()
    }
    
    private fun setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        
        // Configuración de capa de ubicación
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)
        
        // Configurar punto central inicial (Cali, Colombia)
        val startPoint = GeoPoint(3.451647, -76.531982) // Cali, Colombia
        //val startPoint = GeoPoint(40.416775, -3.703790) // Madrid, España
        mapView.controller.setZoom(DEFAULT_ZOOM)
        mapView.controller.setCenter(startPoint)
    }
    
    private fun addSampleMarker() {
        val marker = Marker(mapView)
        marker.position = GeoPoint(40.416775, -3.703790) // Madrid
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Madrid"
        marker.snippet = "Capital de España"
        mapView.overlays.add(marker)
        mapView.invalidate()
    }
    
    private fun centerMapOnLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            
            if (location != null) {
                val geoPoint = GeoPoint(location.latitude, location.longitude)
                mapView.controller.animateTo(geoPoint)
                mapView.controller.setZoom(DEFAULT_ZOOM)
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Permiso de ubicación no disponible", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun requestLocationPermission() {
        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        
        if (!EasyPermissions.hasPermissions(this, *perms)) {
            EasyPermissions.requestPermissions(
                this,
                "Esta app necesita permisos de ubicación para mostrar tu posición en el mapa",
                PERMISSION_REQUEST_CODE,
                *perms
            )
        }
    }
    
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            locationOverlay.enableMyLocation()
            centerMapOnLocation()
        }
    }
    
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(
            this,
            "Esta aplicación requiere permisos para funcionar correctamente",
            Toast.LENGTH_SHORT
        ).show()
    }
}
```

### 4. AndroidManifest.xml

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.osmappdemo">

    <!-- Permisos necesarios -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="28" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:usesCleartextTraffic="true">
        
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

## Características principales

1. **Mapa interactivo** usando la biblioteca OSMDroid
2. **Botón de localización** para centrar el mapa en tu ubicación actual
3. **Marcador de ejemplo** en Madrid
4. **Gestión de permisos** para acceso a ubicación y almacenamiento

## Instrucciones para implementar

1. Cree un nuevo proyecto en Android Studio
2. Agregue las dependencias en el archivo build.gradle a nivel de módulo
3. Cree el layout en activity_main.xml
4. Implemente MainActivity.kt según el código proporcionado
5. Actualice el AndroidManifest.xml con los permisos necesarios

## Funcionamiento

- La aplicación muestra un mapa interactivo centrado inicialmente en Cali
- El botón flotante permite centrar el mapa en la ubicación actual del usuario
- Se muestra un marcador de ejemplo en Madrid con información al hacer clic
- La aplicación gestiona automáticamente los permisos necesarios para funcionar
