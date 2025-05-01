package com.valeria.mapboxproject.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valeria.mapboxproject.data.local.entities.PointEntity
import com.valeria.mapboxproject.data.remote.model.GeoJson
import com.valeria.mapboxproject.data.repository.PointRepository
import com.valeria.mapboxproject.ui.main.GeoJsonUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: PointRepository
) : ViewModel() {

    private val _geoJsonState = MutableStateFlow<GeoJsonUiState>(GeoJsonUiState.Idle)
    val geoJsonState: StateFlow<GeoJsonUiState> = _geoJsonState

    private val _points = MutableStateFlow<List<PointEntity>>(emptyList())
    val points: StateFlow<List<PointEntity>> = _points

    fun loadPoints() {
        viewModelScope.launch {
            repository.getAllPoints().collect {
                _points.value = it
            }
        }
    }

    fun savePoint(name: String, lat: Double, lng: Double, type: String) {
        viewModelScope.launch {
            repository.insertPoint(PointEntity(name = name, latitude = lat, longitude = lng, type = type))
        }
    }

    suspend fun fetchGeoJson(): GeoJson {
        return repository.getRemoteGeoJson()
    }

    fun fetchGeoJsonWithLoading() {
        viewModelScope.launch {
            _geoJsonState.value = GeoJsonUiState.Loading
            try {
                val geoJson = repository.getRemoteGeoJson()
                _geoJsonState.value = GeoJsonUiState.Success(geoJson)
            } catch (e: Exception) {
                _geoJsonState.value = GeoJsonUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}