package com.kampusdolap.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kampusdolap.app.data.ProductRepository
import com.kampusdolap.app.model.Product

class HomeViewModel : ViewModel() {
    private val repository = ProductRepository()
    val products = MutableLiveData<List<Product>>()

    fun loadProducts() {
        repository.getProducts { firebaseProducts ->
            products.postValue(firebaseProducts)
        }
    }
}