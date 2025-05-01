package com.valeria.mapboxproject.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.valeria.mapboxproject.ui.map.MapActivity
import com.valeria.mapboxproject.ui.map.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var permissionsManager: PermissionsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MapViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                viewModel.fetchGeoJsonWithLoading()

                lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                    launch {
                        viewModel.geoJsonState.collect { state ->
                            when (state) {
                                is GeoJsonUiState.Loading -> {
                                    println("Cargando...")
                                }
                                is GeoJsonUiState.Success -> {
                                    println("Datos cargados: ${state.data}")
                                }
                                is GeoJsonUiState.Error -> {
                                    println("Error: ${state.message}")
                                }
                                GeoJsonUiState.Idle -> Unit
                            }
                        }
                    }
                }
            }

            MainScreen {
                goToMap()
            }
        }
        askPermissionForLocation()
    }



    private fun askPermissionForLocation(){
        var permissionsListener: PermissionsListener = object : PermissionsListener {
            override fun onExplanationNeeded(permissionsToExplain: List<String>) {}

            override fun onPermissionResult(granted: Boolean) {
                if (!granted){
                    showPermissionDeniedDialog()
                }
            }
        }

        if (!PermissionsManager.areLocationPermissionsGranted(this)) {
            permissionsManager = PermissionsManager(permissionsListener)
            permissionsManager.requestLocationPermissions(this)
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permiso necesario")
            .setMessage("Esta aplicación necesita permiso de ubicación para funcionar. La aplicación se cerrará.")
            .setCancelable(false)
            .setPositiveButton("Salir") { _, _ ->
                finishAffinity()
            }
            .show()
    }

    private fun goToMap() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}