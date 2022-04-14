package com.example.quicksellapp

import com.example.quicksellapp.model.GetProductsResponse
import com.example.quicksellapp.model.Product


object FakeConstants {
    val mockApiResponse = arrayListOf<GetProductsResponse>(
        GetProductsResponse(1, "Food 1", 14.4f, "Food", "url_image 1"),
        GetProductsResponse(2, "Food 2", 12.4f, "Food", "url_image 2"),
        GetProductsResponse(3, "Food 3", 14.5f, "Food", "url_image 3"),
        GetProductsResponse(4, "Food 4", 14.6f, "Food", "url_image 4"),
        GetProductsResponse(5, "Food 5", 15.6f, "Food", "url_image 5")
        )


    val expectedMockResponse = listOf<Product>(
        Product(5, "Food 5", "Food" , 15.6f, "url_image 5"),
        Product(4, "Food 4", "Food" , 14.6f, "url_image 4"),
        Product(3, "Food 3", "Food" , 14.5f, "url_image 3"),
        Product(1, "Food 1", "Food" , 14.4f, "url_image 1"),
        Product(2, "Food 2", "Food" , 12.4f, "url_image 2")
        )

}