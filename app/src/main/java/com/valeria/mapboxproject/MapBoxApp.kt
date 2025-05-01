package com.valeria.mapboxproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MapBoxApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization code
    }
}