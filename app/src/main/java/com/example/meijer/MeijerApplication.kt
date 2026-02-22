package com.example.meijer

import android.app.Application
import com.example.meijer.di.AppContainer
import com.example.meijer.di.DefaultAppContainer

/**
 * Custom Application class for the Meijer app.
 * Initializes and holds the singleton AppContainer instance.
 */
class MeijerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
