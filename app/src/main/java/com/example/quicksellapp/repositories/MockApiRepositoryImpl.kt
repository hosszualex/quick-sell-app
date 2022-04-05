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
                val deliveryOrders = getDeliveryOrdersFromResponse(data)
                listener.onSuccess(deliveryOrders)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }


    private fun getDeliveryOrdersFromResponse(data: ArrayList<GetProductsResponse>): List<Product> {
        val products = mutableListOf<Product>()
        data.forEach { order ->
            products.add(
                Product(order.id, order.name, order.category, order.price, order.url_image)
            )
        }
        products.sortByDescending { it.price }
        return products.toList()
    }
}