package com.example.quicksellapp.repositories

import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.Product

interface IProductRepository {

    fun getProducts(listener: IOnGetProducts)

    interface IOnGetProducts{
        fun onSuccess(products: List<Product>)
        fun onFailed(error: ErrorResponse)
    }
}