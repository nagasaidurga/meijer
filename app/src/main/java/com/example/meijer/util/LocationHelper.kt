package com.example.meijer.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import java.util.Locale

/**
 * Helper class for location-related operations.
 * Handles fetching the user's current location and converting coordinates to city name.
 * 
 * This class uses Google Play Services Location API to get the device location
 * and Geocoder to convert coordinates to a readable city name.
 */
class LocationHelper(private val context: Context) {
    
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    
    /**
     * Gets the current city name based on the device's location.
     * 
     * @return The city name as a String, or "Unknown" if location cannot be determined
     */
    suspend fun getCurrentCityName(): String {
        return try {
            // Check if location permission is granted
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return "Unknown"
            }
            
            // Get last known location
            val location: Location? = fusedLocationClient.lastLocation.await()
            
            if (location != null) {
                // Use Geocoder to convert coordinates to address
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                
                if (addresses != null && addresses.isNotEmpty()) {
                    addresses[0].locality ?: "Unknown"
                } else {
                    "Unknown"
                }
            } else {
                "Unknown"
            }
        } catch (e: Exception) {
            // Return default city name if location cannot be determined
            "Unknown"
        }
    }
}

