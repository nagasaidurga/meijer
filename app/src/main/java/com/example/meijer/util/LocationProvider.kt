package com.example.meijer.util

interface LocationProvider {
    suspend fun getCurrentCityName(): String?
}
