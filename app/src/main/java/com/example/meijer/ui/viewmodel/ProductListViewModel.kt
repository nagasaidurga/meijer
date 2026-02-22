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
 */
class ProductListViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    /**
     * Called when the screen is first displayed.
     */
    fun onStart() {
        loadProducts()
    }

    /**
     * Fetches the list of products from the repository.
     */
    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductListUiState(isLoading = true)
            repository.getProducts()
                .onSuccess {
                    _uiState.value = ProductListUiState(products = it)
                }
                .onFailure {
                    _uiState.value = ProductListUiState(error = it.message)
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
