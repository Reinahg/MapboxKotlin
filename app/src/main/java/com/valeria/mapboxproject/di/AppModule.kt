package com.valeria.mapboxproject.di

import android.content.Context
import androidx.room.Room
import dagger.Provides
import com.valeria.mapboxproject.data.local.dao.PointDao
import com.valeria.mapboxproject.data.local.database.AppDatabase
import com.valeria.mapboxproject.data.remote.ApiService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://d2ad6b4ur7yvpq.cloudfront.net/naturalearth-3.3.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "points_db"
        ).build()

    @Provides
    fun providePointDao(db: AppDatabase): PointDao = db.pointDao()
}