package com.example.meijer.ui.state

import com.example.meijer.data.model.Product

/**
 * UI state class for the Product List screen.
 * Represents all possible states the product list screen can be in.
 * 
 * @param isLoading Indicates if data is currently being fetched
 * @param products List of products to display, null if not loaded yet
 * @param error Error message if something went wrong, null if no error
 */
data class ProductListUiState(
    val isLoading: Boolean = false,
    val products: List<Product>? = null,
    val error: String? = null
)

