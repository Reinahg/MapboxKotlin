package com.valeria.mapboxproject.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
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
    private var loadingDialog: AlertDialog? = null
    private val viewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                viewModel.fetchGeoJsonWithLoading()

                lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                    launch {
                        viewModel.geoJsonState.collect { state ->
                            when (state) {
                                is GeoJsonUiState.Loading -> {
                                    showLoadingDialog()
                                }
                                is GeoJsonUiState.Success -> {
                                    hideLoadingDialog()
                                    println("Datos cargados")
                                }
                                is GeoJsonUiState.Error -> {
                                    hideLoadingDialog()
                                    showErrorDialog(state.message)
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

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = AlertDialog.Builder(this)
                .setTitle("Cargando")
                .setMessage("Por favor, espera mientras cargamos los datos...")
                .setCancelable(false)
                .create()
        }
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error al cargar datos")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
            .show()
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