package com.valeria.mapboxproject.ui.map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.valeria.mapboxproject.ui.components.map.MapScreenContent
import com.valeria.mapboxproject.ui.theme.MapBoxProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : ComponentActivity() {
    private val viewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                viewModel.loadPoints()
            }

            MapBoxProjectTheme {
                MapScreenContent(viewModel)
            }
        }
    }
}



