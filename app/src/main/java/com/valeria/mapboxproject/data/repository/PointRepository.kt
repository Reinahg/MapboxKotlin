package com.valeria.mapboxproject.data.repository

import com.valeria.mapboxproject.data.local.dao.PointDao
import com.valeria.mapboxproject.data.local.entities.PointEntity
import com.valeria.mapboxproject.data.remote.ApiService
import com.valeria.mapboxproject.data.remote.model.GeoJson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointRepository @Inject constructor(
    private val api: ApiService,
    private val dao: PointDao
) {
    suspend fun getRemoteGeoJson(): GeoJson = api.getGeoJson()

    suspend fun insertPoint(point: PointEntity) = dao.insert(point)

    fun getAllPoints(): Flow<List<PointEntity>> = dao.getAllPoints()

    fun getFavoritePoints(): Flow<List<PointEntity>> {
        return dao.getFavoritePoints()
    }
}