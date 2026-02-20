package com.example.meijer.util

import android.content.Context
import coil.ImageLoader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Utility class for creating a custom Coil ImageLoader with proper HTTP headers.
 * This is necessary because some websites (like meijer.com) require proper User-Agent
 * and other headers to allow image loading.
 */
object CoilImageLoader {
    
    /**
     * Creates a custom ImageLoader with OkHttp client that includes proper headers.
     * 
     * @param context Application context
     * @return Configured ImageLoader instance
     */
    fun create(context: Context): ImageLoader {
        // Create OkHttp client with logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            // Add interceptor to set User-Agent and other headers
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    // Add User-Agent header (required by many websites)
                    .header("User-Agent", "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36")
                    // Add Accept header
                    .header("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                    // Add Accept-Language header
                    .header("Accept-Language", "en-US,en;q=0.9")
                    // Add Referer header (some sites check this)
                    .header("Referer", "https://www.meijer.com/")
                    .build()
                chain.proceed(requestBuilder)
            }
            .build()
        
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .build()
    }
}

