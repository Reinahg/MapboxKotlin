package com.valeria.mapboxproject.data.remote

import com.valeria.mapboxproject.data.remote.model.GeoJson
import retrofit2.http.GET

interface ApiService {
    @GET("ne_50m_populated_places_simple.geojson")
    suspend fun getGeoJson(): GeoJson
}