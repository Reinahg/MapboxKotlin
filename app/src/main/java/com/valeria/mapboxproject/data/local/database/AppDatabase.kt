package com.valeria.mapboxproject.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valeria.mapboxproject.data.local.dao.PointDao
import com.valeria.mapboxproject.data.local.entities.PointEntity

@Database(
    entities = [PointEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao
}