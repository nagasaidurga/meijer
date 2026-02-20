package com.example.meijer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.compose.LocalImageLoader
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meijer.navigation.NavGraph
import com.example.meijer.ui.theme.MeijerTheme
import com.example.meijer.ui.viewmodel.ProductDetailViewModel
import com.example.meijer.ui.viewmodel.ProductListViewModel
import com.example.meijer.util.CoilImageLoader

/**
 * Main Activity for the Meijer Product Information app.
 * Sets up the navigation graph and initializes ViewModels.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Create custom ImageLoader with proper headers for meijer.com
        val imageLoader: ImageLoader = CoilImageLoader.create(this)
        
        setContent {
            MeijerTheme {
                // Provide custom ImageLoader to all Coil image requests
                CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // Initialize ViewModels
                        val productListViewModel: ProductListViewModel = viewModel()
                        val productDetailViewModel: ProductDetailViewModel = viewModel()
                        
                        // Set up navigation
                        NavGraph(
                            productListViewModel = productListViewModel,
                            productDetailViewModel = productDetailViewModel
                        )
                    }
                }
            }
        }
    }
}