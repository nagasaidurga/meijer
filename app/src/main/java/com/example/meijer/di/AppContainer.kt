package com.example.meijer.di

import android.content.Context
import com.example.meijer.data.api.AndroidLoggerService
import com.example.meijer.data.api.LoggerService
import com.example.meijer.data.api.ProductApiService
import com.example.meijer.data.api.RetrofitClient
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.util.DefaultLocationProvider
import com.example.meijer.util.LocationProvider

/**
 * A simple DI container for managing dependencies.
 */
interface AppContainer {
    val productRepository: ProductRepository
    val locationProvider: LocationProvider
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val loggerService: LoggerService by lazy {
        AndroidLoggerService()
    }

    private val productApiService: ProductApiService by lazy {
        RetrofitClient.create(loggerService)
    }

    override val productRepository: ProductRepository by lazy {
        ProductRepository(productApiService)
    }

    override val locationProvider: LocationProvider by lazy {
        DefaultLocationProvider(context)
    }
}
