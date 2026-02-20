package com.example.meijer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meijer.data.model.Product
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.ui.state.ProductListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Product List screen.
 * Manages the state and business logic for displaying the list of products.
 * 
 * Responsibilities:
 * - Fetching product list from repository
 * - Managing loading and error states
 * - Providing UI state to the Compose screen
 */
class ProductListViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {
    
    // Mutable state flow for internal state management
    private val _uiState = MutableStateFlow(ProductListUiState(isLoading = true))
    
    // Public read-only state flow exposed to UI
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()
    
    init {
        // Load products when ViewModel is created
        loadProducts()
    }
    
    /**
     * Fetches the list of products from the repository.
     * Updates the UI state based on the result (loading, success, or error).
     */
    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.getProducts()
                .onSuccess { products ->
                    _uiState.value = ProductListUiState(
                        isLoading = false,
                        products = products,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _uiState.value = ProductListUiState(
                        isLoading = false,
                        products = null,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    /**
     * Retry loading products in case of an error.
     */
    fun retry() {
        loadProducts()
    }
}

