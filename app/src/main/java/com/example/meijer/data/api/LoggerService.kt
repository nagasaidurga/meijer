package com.example.meijer.data.api

/**
 * Interface for logging messages.
 * This allows for different logging implementations in production and test environments.
 */
interface LoggerService {
    fun log(tag: String, message: String)
}
