package com.example.meijer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.ui.state.ProductDetailUiState
import com.example.meijer.util.LocationProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Product Detail screen.
 * Manages the state and business logic for displaying detailed product information.
 */
class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState(isLoading = true))
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    /**
     * Loads product details for a specific product ID.
     * Also fetches the current city name for sharing functionality.
     */
    fun loadProductDetail(productId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val productDetailResult = repository.getProductDetail(productId)
            val cityName = locationProvider.getCurrentCityName()

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
     */
    fun retry(productId: Int) {
        loadProductDetail(productId)
    }

    /**
     * Generates the shareable text for the "Add to List" functionality.
     */
    fun getShareableText(): String {
        val state = _uiState.value
        val productDetail = state.productDetail

        return if (productDetail != null) {
            "${productDetail.title} - ${productDetail.price} from ${state.cityName ?: "Unknown"} added to list"
        } else {
            ""
        }
    }
}
