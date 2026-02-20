package com.example.meijer.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit client configuration for API service.
 * Provides a singleton instance of ProductApiService with proper configuration.
 * 
 * Base URL: https://meijer-maui-test-default-rtdb.firebaseio.com/
 */
object RetrofitClient {
    
    // Base URL for the Meijer Firebase Realtime Database API
    private const val BASE_URL = "https://meijer-maui-test-default-rtdb.firebaseio.com/"
    
    /**
     * Creates and configures OkHttpClient with logging interceptor for debugging.
     * Logs HTTP requests and responses in debug mode.
     */
    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    /**
     * Creates Retrofit instance with Gson converter and OkHttpClient.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    /**
     * Provides singleton instance of ProductApiService.
     * Use this to make network calls to the product API endpoints.
     */
    val productApiService: ProductApiService = retrofit.create(ProductApiService::class.java)
}

