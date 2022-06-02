package com.example.quicksellapp.model

data class GetProductsResponse(
    val id: Int,
    val name: String,
    val price: Float,
    val category: String,
    val url_image: String
)