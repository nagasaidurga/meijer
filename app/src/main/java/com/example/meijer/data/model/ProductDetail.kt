package com.example.meijer.data.model

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
    val id: Int,
    val title: String,
    val summary: String,
    val description: String,
    val price: String,
    val imageUrl: String
)
