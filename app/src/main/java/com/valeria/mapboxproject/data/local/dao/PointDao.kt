package com.valeria.mapboxproject.data.local.dao

import androidx.room.*
import com.valeria.mapboxproject.data.local.entities.PointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(point: PointEntity)

    @Delete
    suspend fun delete(point: PointEntity)

    @Query("SELECT * FROM points ORDER BY name ASC")
    fun getAllPoints(): Flow<List<PointEntity>>

    @Query("SELECT * FROM points WHERE id = :id")
    suspend fun getPointById(id: Int): PointEntity?

    @Query("SELECT * FROM points WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoritePoints(): Flow<List<PointEntity>>
}