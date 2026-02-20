package com.example.meijer.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data model representing a product summary from the Products API.
 * This model contains basic product information displayed in the product list.
 *
 * @param id Unique identifier for the product
 * @param title Product name/title
 * @param summary Brief description of the product
 * @param imageUrl URL to the product image
 */
data class Product(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("summary")
    val summary: String,
    
    @SerializedName("imageUrl")
    val imageUrl: String
)

