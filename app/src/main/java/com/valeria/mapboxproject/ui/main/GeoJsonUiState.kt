package com.valeria.mapboxproject.ui.main

sealed class GeoJsonUiState {
    object Loading : GeoJsonUiState()
    object Success : GeoJsonUiState()
    data class Error(val message: String) : GeoJsonUiState()
    object Idle : GeoJsonUiState()
}