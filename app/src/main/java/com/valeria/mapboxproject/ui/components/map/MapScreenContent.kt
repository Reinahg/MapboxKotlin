package com.valeria.mapboxproject.ui.components.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.rememberStyleState
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.valeria.mapboxproject.R
import com.valeria.mapboxproject.data.local.entities.PointEntity
import com.valeria.mapboxproject.ui.components.LoadingIndicator
import com.valeria.mapboxproject.ui.components.favorite.AddPointDialog
import com.valeria.mapboxproject.ui.components.favorite.FavoritesBox
import com.valeria.mapboxproject.ui.map.MapNavigator
import com.valeria.mapboxproject.ui.map.MapViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MapScreenContent(viewModel: MapViewModel) {
    val points by viewModel.points.collectAsState()
    var isLoading by remember { mutableStateOf(true) }
    val currentStyle by viewModel.currentStyle.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedLatLng by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var inputName by remember { mutableStateOf("") }
    var showFavorites by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            val mapViewportState = rememberMapViewportState()
            val mapNavigator = remember { MapNavigator(mapViewportState) }
            MapboxMap(
                modifier = Modifier.fillMaxSize(),
                mapViewportState = mapViewportState,
                style = {
                    MapStyle(
                        style = currentStyle.styleUri,
                        styleState = rememberStyleState {
                            styleInteractionsState
                                .onMapLongClicked { context ->
                                    val coordinate = context.coordinateInfo.coordinate
                                    selectedLatLng = coordinate.latitude() to coordinate.longitude()
                                    showAddDialog = true
                                    true
                                }
                        })
                }
            ) {
                MapInitialSetup(mapViewportState)

                MapEffect(points) { mapView ->
                    if (points.isNotEmpty()) {
                        CoroutineScope(Dispatchers.Main).launch {
                            isLoading = true
                            try {
                                prepareMarkers(mapView, points)
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                }
            }
            MapActions(
                modifier = Modifier.align(Alignment.BottomEnd),
                mapNavigator = mapNavigator,
                viewModel = viewModel) {
                showFavorites = !showFavorites
            }
            if (isLoading) {
                LoadingIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if (showAddDialog && selectedLatLng != null) {
                AddPointDialog(
                    showDialog = showAddDialog,
                    onDismiss = {
                        showAddDialog = false
                        inputName = ""
                    },
                    onConfirm = { name, type ->
                        val (lat, lng) = selectedLatLng!!
                        viewModel.saveFavoritePoint(name, lat, lng, type)
                        showAddDialog = false
                        inputName = ""
                    },
                    inputName = inputName,
                    onNameChange = { inputName = it }
                )
            }

            if (showFavorites) {
                FavoritesBox(
                    modifier = Modifier.align(Alignment.BottomStart),
                    viewModel = viewModel,
                    navigator = mapNavigator) {
                    showFavorites = false
                }
            }
        }
    }
}

private suspend fun prepareMarkers(
    mapView: MapView,
    points: List<PointEntity>
) = withContext(Dispatchers.Main) {
    try {
        val annotationManager = mapView.annotations.createPointAnnotationManager()
        annotationManager.deleteAll()

        val bitmapRed = withContext(Dispatchers.IO) {
            BitmapFactory.decodeResource(mapView.context.resources, R.drawable.red_marker)
        }
        val bitmapBlue = withContext(Dispatchers.IO) {
            BitmapFactory.decodeResource(mapView.context.resources, R.drawable.blue_alert)
        }

        val markerBitmapRed = Bitmap.createScaledBitmap(bitmapRed, 48, 48, true)
        val markerBitmapBlue = Bitmap.createScaledBitmap(bitmapBlue, 48, 48, true)

        points.chunked(100).forEach { chunk ->
            val annotations = chunk.map { point ->
                PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(point.longitude, point.latitude))
                    .withIconImage(if (point.type.equals("blue")) markerBitmapBlue else markerBitmapRed)
            }
            annotationManager.create(annotations)
        }
    } catch (e: Exception) {
        Log.e("MapError", "Error al preparar marcadores", e)
    }
}