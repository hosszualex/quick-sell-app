package com.example.quicksellapp.retrofit

import com.example.quicksellapp.Constants
import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.GetProductsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Inject

class MockApiRetrofitService @Inject constructor(private val mockApiService: IMockApiService): IMockApiRetrofitService {

    interface IMockApiService {
        @GET("products")
        fun getProducts(): Call<ArrayList<GetProductsResponse>>
    }

    override fun getProducts(listener: IMockApiRetrofitService.IOnGetProducts) {
        val request = mockApiService.getProducts()
        request.enqueue(object: Callback<ArrayList<GetProductsResponse>> {
            override fun onResponse(
                call: Call<ArrayList<GetProductsResponse>>,
                response: Response<ArrayList<GetProductsResponse>>
            ) {
                if(response.isSuccessful) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorResponse =
                        ErrorResponse(response.errorBody().toString(), response.code())
                    listener.onFailed(errorResponse)
                }
            }

            override fun onFailure(call: Call<ArrayList<GetProductsResponse>>, t: Throwable) {
                val errorResponse =
                    ErrorResponse(t.message.toString(), Constants.SERVER_CALL_FAILED_ERROR_CODE)
                listener.onFailed(errorResponse)
            }
        })
    }
}