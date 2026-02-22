package com.example.meijer.data.model

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
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String
)
