package com.example.meijer.ui.state

import com.example.meijer.data.model.ProductDetail

/**
 * UI state class for the Product Detail screen.
 * Represents all possible states the product detail screen can be in.
 * 
 * @param isLoading Indicates if data is currently being fetched
 * @param productDetail Detailed product information, null if not loaded yet
 * @param error Error message if something went wrong, null if no error
 * @param cityName Current city name for sharing functionality
 */
data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val productDetail: ProductDetail? = null,
    val error: String? = null,
    val cityName: String = "Unknown"
)

