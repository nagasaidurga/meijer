package com.example.meijer.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.ui.state.ProductDetailUiState
import com.example.meijer.util.LocationHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Product Detail screen.
 * Manages the state and business logic for displaying detailed product information.
 * 
 * Responsibilities:
 * - Fetching product details from repository
 * - Getting current city name for sharing functionality
 * - Managing loading and error states
 * - Providing UI state to the Compose screen
 */
class ProductDetailViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {
    
    // Mutable state flow for internal state management
    private val _uiState = MutableStateFlow(ProductDetailUiState(isLoading = true))
    
    // Public read-only state flow exposed to UI
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()
    
    /**
     * Loads product details for a specific product ID.
     * Also fetches the current city name for sharing functionality.
     * 
     * @param productId The unique identifier of the product to load
     * @param context Application context for location services
     */
    fun loadProductDetail(productId: Int, context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // Fetch product detail and city name in parallel
            val productDetailResult = repository.getProductDetail(productId)
            val locationHelper = LocationHelper(context)
            val cityName = locationHelper.getCurrentCityName()
            
            productDetailResult
                .onSuccess { productDetail ->
                    _uiState.value = ProductDetailUiState(
                        isLoading = false,
                        productDetail = productDetail,
                        error = null,
                        cityName = cityName
                    )
                }
                .onFailure { exception ->
                    _uiState.value = ProductDetailUiState(
                        isLoading = false,
                        productDetail = null,
                        error = exception.message ?: "Unknown error occurred",
                        cityName = cityName
                    )
                }
        }
    }
    
    /**
     * Retry loading product detail in case of an error.
     * 
     * @param productId The unique identifier of the product to retry loading
     * @param context Application context for location services
     */
    fun retry(productId: Int, context: Context) {
        loadProductDetail(productId, context)
    }
    
    /**
     * Generates the shareable text for the "Add to List" functionality.
     * Format: "{product title} - {price} from {city name} added to list"
     * 
     * @return The formatted shareable text string
     */
    fun getShareableText(): String {
        val state = _uiState.value
        val productDetail = state.productDetail
        
        return if (productDetail != null) {
            "${productDetail.title} - ${productDetail.price} from ${state.cityName} added to list"
        } else {
            ""
        }
    }
}

