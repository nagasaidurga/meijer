package com.example.meijer.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit client configuration for API service.
 * Provides a factory method to create an instance of ProductApiService with proper configuration.
 */
object RetrofitClient {

    // Base URL for the Meijer Firebase Realtime Database API
    private const val BASE_URL = "https://meijer-maui-test-default-rtdb.firebaseio.com/"

    /**
     * Creates and configures OkHttpClient with a logging interceptor for debugging.
     *
     * @param loggerService The logging service to use for logging HTTP requests and responses.
     * @return An instance of OkHttpClient.
     */
    private fun createOkHttpClient(loggerService: LoggerService): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            loggerService.log("Retrofit", message)
        }.apply {
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
     * Creates a Retrofit instance for the ProductApiService.
     *
     * @param loggerService The logging service for the OkHttpClient.
     * @return An instance of ProductApiService.
     */
    fun create(loggerService: LoggerService): ProductApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(loggerService))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ProductApiService::class.java)
    }
}
