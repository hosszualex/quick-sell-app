package com.example.quicksellapp.screens.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel: ViewModel() {

    private val _onHasPayed = MutableLiveData<Boolean>()
    val onHasPayed: LiveData<Boolean>
        get() =  _onHasPayed

    fun hasPayedProducts(hasPayed: Boolean) {
        _onHasPayed.value = hasPayed
    }
}