package com.example.quicksellapp.screens.quicksell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.Product
import com.example.quicksellapp.repositories.IProductRepository
import com.example.quicksellapp.repositories.MockApiRepositoryImpl

class QuickSellViewModel: ViewModel() {
    private val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError

    private val _onGetProducts = MutableLiveData<List<Product>>()
    val onGetProducts: LiveData<List<Product>>
        get() = _onGetProducts

    private val repository: IProductRepository = MockApiRepositoryImpl()

    fun retrieveProducts() {
        if (_isBusy.value != null && _isBusy.value == true) {
            return
        }

        repository.getProducts(object: IProductRepository.IOnGetProducts{
            override fun onSuccess(products: List<Product>) {
                _onGetProducts.value = products
                _isBusy.value = false
            }

            override fun onFailed(error: ErrorResponse) {
                _onError.value = error
                _isBusy.value = false
            }
        })
    }
}