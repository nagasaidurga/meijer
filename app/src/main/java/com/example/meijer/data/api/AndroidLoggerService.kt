package com.example.meijer.data.api

import android.util.Log

/**
 * Android-specific implementation of LoggerService.
 * Uses the Android Log class for logging.
 */
class AndroidLoggerService : LoggerService {
    override fun log(tag: String, message: String) {
        if (Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message)
        }
    }
}
