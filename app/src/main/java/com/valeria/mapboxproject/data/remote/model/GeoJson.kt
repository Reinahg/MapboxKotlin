package com.valeria.mapboxproject.data.remote.model

data class GeoJson(
    val type: String,
    val features: List<Feature>
)

data class Feature(
    val type: String,
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val name: String?,
    val adm0name: String?,
    val adm1name: String?,
    val latitude: Double,
    val longitude: Double,
    val pop_max: Int
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)