package com.valeria.mapboxproject.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valeria.mapboxproject.data.local.entities.PointEntity
import com.valeria.mapboxproject.data.repository.PointRepository
import com.valeria.mapboxproject.ui.main.GeoJsonUiState
import com.valeria.mapboxproject.utils.MapStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
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

    private val _currentStyle = MutableStateFlow(MapStyle.STREETS)
    val currentStyle: StateFlow<MapStyle> = _currentStyle

    val favoritePoints = repository.getFavoritePoints().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun loadPoints() {
        viewModelScope.launch {
            repository.getAllPoints().collect {
                _points.value = it
            }
        }
    }

    fun changeMapStyle(style: MapStyle) {
        _currentStyle.value = style
    }

    fun saveFavoritePoint(name: String, lat: Double, lng: Double, type: String) {
        viewModelScope.launch {
            if (name.isNotEmpty()) {
                repository.insertPoint(
                    PointEntity(
                        name = name,
                        latitude = lat,
                        longitude = lng,
                        isFavorite = true,
                        type = type
                    )
                )
            }
        }
    }

    fun fetchGeoJsonWithLoading() {
        viewModelScope.launch {
            _geoJsonState.value = GeoJsonUiState.Loading

            try {
                val existingPoints = repository.getAllPoints().first()

                if (existingPoints.isEmpty()) {
                    val geoJson = repository.getRemoteGeoJson()

                    val pointEntities = geoJson.features.map {
                        PointEntity(
                            name = it.properties.name.toString(),
                            latitude = it.geometry.coordinates[1],
                            longitude = it.geometry.coordinates[0],
                            type = it.geometry.type
                        )
                    }

                    pointEntities.forEach { repository.insertPoint(it) }

                    _geoJsonState.value = GeoJsonUiState.Success
                } else {
                    _geoJsonState.value = GeoJsonUiState.Success
                }

            } catch (e: Exception) {
                _geoJsonState.value = GeoJsonUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}