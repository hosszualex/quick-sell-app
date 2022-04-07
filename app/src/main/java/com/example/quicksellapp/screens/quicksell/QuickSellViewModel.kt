package com.example.quicksellapp.screens.quicksell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.Product
import com.example.quicksellapp.repositories.IProductRepository
import com.example.quicksellapp.repositories.MockApiRepositoryImpl

class QuickSellViewModel: ViewModel() {
    private val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError

    private val _onGetProducts = MutableLiveData<List<Product>>()
    val onGetProducts: LiveData<List<Product>>
        get() = _onGetProducts

    private val _onNavigate = MutableLiveData<Float>()
    val onNavigate: LiveData<Float>
        get() = _onNavigate

    private var isBusy = false
    private val repository: IProductRepository = MockApiRepositoryImpl()

    fun retrieveProducts() {
        if (isBusy) {
            return
        }

        repository.getProducts(object: IProductRepository.IOnGetProducts{
            override fun onSuccess(products: List<Product>) {
                _onGetProducts.value = products
                isBusy = false
            }

            override fun onFailed(error: ErrorResponse) {
                _onError.value = error
                isBusy = false
            }
        })
    }

    fun updateProductAmount(product: Product, amount: Int) {
        val productToUpdate = _onGetProducts.value?.find { it.id == product.id }
        productToUpdate?.amount = amount
    }

    fun onPayClicked() {
        var totalPayAmount: Float = 0f
        _onGetProducts.value?.forEach { product ->
            if (product.amount != 0) {
                totalPayAmount += product.price * product.amount
            }
        }
        _onNavigate.value = totalPayAmount
    }
}