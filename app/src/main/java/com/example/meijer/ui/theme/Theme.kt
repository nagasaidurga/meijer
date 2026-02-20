package com.example.meijer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark color scheme for Meijer app (if needed in future)
 */
private val DarkColorScheme = darkColorScheme(
    primary = MeijerBlue,
    secondary = MeijerRed,
    tertiary = MeijerRed,
    background = Color(0xFF1A1A1A),
    surface = Color(0xFF2A2A2A),
    onPrimary = MeijerTextOnBlue,
    onSecondary = MeijerTextOnBlue,
    onTertiary = MeijerTextOnBlue,
    onBackground = MeijerTextOnBlue,
    onSurface = MeijerTextOnBlue,
    error = MeijerRed
)

/**
 * Light color scheme matching Meijer app design.
 * Uses Meijer blue for primary actions, red for special labels,
 * and appropriate text colors for readability.
 */
private val LightColorScheme = lightColorScheme(
    // Primary colors - Meijer Blue for headers and buttons
    primary = MeijerBlue,
    onPrimary = MeijerTextOnBlue, // White text on blue
    
    // Secondary colors - Meijer Red for special labels
    secondary = MeijerRed,
    onSecondary = MeijerTextOnBlue, // White text on red
    
    // Tertiary colors
    tertiary = MeijerRed,
    onTertiary = MeijerTextOnBlue,
    
    // Background colors - White for clean look
    background = MeijerBackground,
    onBackground = MeijerTextPrimary, // Black text on white
    
    // Surface colors - White surfaces
    surface = MeijerSurface,
    onSurface = MeijerTextPrimary, // Black text on white
    
    // Error color - Meijer Red
    error = MeijerRed,
    onError = MeijerTextOnBlue,
    
    // Surface variants for cards, search bars, etc.
    surfaceVariant = MeijerSearchBar,
    onSurfaceVariant = MeijerTextSecondary, // Gray text for secondary content
    
    // Outline colors for borders
    outline = MeijerBorder,
    outlineVariant = MeijerDivider
)

@Composable
fun MeijerTheme(
    darkTheme: Boolean = false, // Default to light theme to match Meijer app
    // Disable dynamic color to maintain consistent Meijer branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Disable dynamic colors to maintain Meijer brand consistency
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        //     val context = LocalContext.current
        //     if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        // }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    // Set status bar color to match Meijer blue header
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
