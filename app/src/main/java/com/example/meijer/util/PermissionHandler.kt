package com.example.meijer.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Utility class for checking and handling runtime permissions.
 * Specifically handles location permissions required for getting city name.
 */
object PermissionHandler {
    
    /**
     * Checks if location permissions are granted.
     * 
     * @param context Application context
     * @return true if both fine and coarse location permissions are granted, false otherwise
     */
    fun hasLocationPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}

