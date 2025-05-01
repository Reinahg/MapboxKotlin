package com.valeria.mapboxproject.utils

enum class MapStyle(val styleUri: String) {
    STREETS("mapbox://styles/mapbox/streets-v12"),
    SATELLITE("mapbox://styles/mapbox/satellite-v9"),
    DARK("mapbox://styles/mapbox/dark-v11"),
    LIGHT("mapbox://styles/mapbox/light-v11")
}