package com.example.meijer.data.api

import com.example.meijer.data.model.Product
import com.example.meijer.data.model.ProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API service interface for Meijer product endpoints.
 * Defines the network calls for fetching product data from Firebase Realtime Database.
 */
interface ProductApiService {
    
    /**
     * Fetches the list of all products.
     * 
     * @return Response containing a list of Product objects
     * 
     * Endpoint: https://meijer-maui-test-default-rtdb.firebaseio.com/products.json
     */
    @GET("products.json")
    suspend fun getProducts(): Response<List<Product>>
    
    /**
     * Fetches detailed information for a specific product by ID.
     * 
     * @param productId The unique identifier of the product
     * @return Response containing ProductDetail object
     * 
     * Endpoint: https://meijer-maui-test-default-rtdb.firebaseio.com/product-details/{productId}.json
     */
    @GET("product-details/{productId}.json")
    suspend fun getProductDetail(@Path("productId") productId: Int): Response<ProductDetail>
}

