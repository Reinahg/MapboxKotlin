package com.valeria.mapboxproject.ui.main

import com.valeria.mapboxproject.data.remote.model.GeoJson

sealed class GeoJsonUiState {
    object Loading : GeoJsonUiState()
    data class Success(val data: GeoJson) : GeoJsonUiState()
    data class Error(val message: String) : GeoJsonUiState()
    object Idle : GeoJsonUiState()
}