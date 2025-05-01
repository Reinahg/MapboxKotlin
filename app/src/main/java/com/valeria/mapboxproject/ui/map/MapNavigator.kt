package com.valeria.mapboxproject.ui.map

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.valeria.mapboxproject.data.local.entities.PointEntity

class MapNavigator(private val mapViewportState: MapViewportState) {

    fun navigateToCurrentLocation() {
        mapViewportState.transitionToFollowPuckState()
    }

    fun navigateToSavedPlace(place: PointEntity) {
        val cameraOptions = CameraOptions.Builder()
            .center(Point.fromLngLat(place.longitude, place.latitude))
            .zoom(16.0)
            .build()
        mapViewportState.flyTo(cameraOptions)
    }
}