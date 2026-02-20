package com.example.meijer.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data model representing detailed product information from the Product Detail API.
 * This model contains comprehensive product information displayed in the product detail screen.
 *
 * @param id Unique identifier for the product
 * @param title Product name/title
 * @param summary Brief description of the product
 * @param description Detailed description of the product
 * @param price Product price with unit (e.g., "$0.59/lb")
 * @param imageUrl URL to the full product image
 */
data class ProductDetail(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("summary")
    val summary: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("price")
    val price: String,
    
    @SerializedName("imageUrl")
    val imageUrl: String
)

