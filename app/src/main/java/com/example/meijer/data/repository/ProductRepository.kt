package com.example.meijer.data.repository

import com.example.meijer.data.api.RetrofitClient
import com.example.meijer.data.model.Product
import com.example.meijer.data.model.ProductDetail

/**
 * Repository class for managing product data operations.
 * Acts as a single source of truth for product data, abstracting data sources from ViewModels.
 * 
 * This repository handles:
 * - Fetching product list from API
 * - Fetching product details from API
 * - Error handling and data transformation
 */
class ProductRepository {
    
    private val apiService = RetrofitClient.productApiService
    
    /**
     * Fetches the list of all products from the API.
     * 
     * @return Result containing either a list of Product objects or an error
     */
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch products: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Fetches detailed information for a specific product by ID.
     * 
     * @param productId The unique identifier of the product
     * @return Result containing either a ProductDetail object or an error
     */
    suspend fun getProductDetail(productId: Int): Result<ProductDetail> {
        return try {
            val response = apiService.getProductDetail(productId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch product detail: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

