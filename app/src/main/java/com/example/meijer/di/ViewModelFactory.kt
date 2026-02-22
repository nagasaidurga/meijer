package com.example.meijer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meijer.data.repository.ProductRepository
import com.example.meijer.ui.viewmodel.ProductDetailViewModel
import com.example.meijer.ui.viewmodel.ProductListViewModel
import com.example.meijer.util.LocationProvider

/**
 * Factory for creating ViewModels with dependencies.
 */
class ViewModelFactory(
    private val repository: ProductRepository,
    private val locationProvider: LocationProvider
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(repository, locationProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
