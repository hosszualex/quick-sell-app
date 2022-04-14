package com.example.quicksellapp.services

import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.GetProductsResponse
import com.example.quicksellapp.retrofit.IMockApiRetrofitService

object MockApiFakeRetrofitService: IMockApiRetrofitService {

    var mockData: ArrayList<GetProductsResponse>? = null
    var responseCode: Int = 200

    override fun getProducts(listener: IMockApiRetrofitService.IOnGetProducts) {
        when(responseCode) {
            200 -> {
                if (mockData != null) {
                    listener.onSuccess(mockData!!)
                }
            }
            400 -> {
                listener.onFailed(ErrorResponse("Server is unreachable", 400))
            }
        }
    }
}