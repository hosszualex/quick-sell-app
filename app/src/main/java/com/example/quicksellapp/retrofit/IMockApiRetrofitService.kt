package com.example.quicksellapp.retrofit

import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.GetProductsResponse

interface IMockApiRetrofitService {

    fun getProducts(listener: IOnGetProducts)

    interface IOnGetProducts {
        fun onSuccess(data: ArrayList<GetProductsResponse>)
        fun onFailed(error: ErrorResponse)
    }
}