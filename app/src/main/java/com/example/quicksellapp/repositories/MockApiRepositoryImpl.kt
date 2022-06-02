package com.example.quicksellapp.repositories

import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.GetProductsResponse
import com.example.quicksellapp.model.Product
import com.example.quicksellapp.retrofit.IMockApiRetrofitService
import com.example.quicksellapp.retrofit.MockApiRetrofitService

class MockApiRepositoryImpl : IProductRepository {
    private val retrofitService: IMockApiRetrofitService

    init {
        retrofitService = MockApiRetrofitService
    }

    override fun getProducts(listener: IProductRepository.IOnGetProducts) {
        retrofitService.getProducts(object : IMockApiRetrofitService.IOnGetProducts {
            override fun onSuccess(data: ArrayList<GetProductsResponse>) {
                val products = getProductsFromResponse(data)
                listener.onSuccess(products)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }


    private fun getProductsFromResponse(data: ArrayList<GetProductsResponse>): List<Product> {
        val products = mutableListOf<Product>()
        data.forEach { product ->
            products.add(
                Product(product.id, product.name, product.category, product.price, product.url_image)
            )
        }
        products.sortByDescending { it.price }
        return products.toList()
    }
}