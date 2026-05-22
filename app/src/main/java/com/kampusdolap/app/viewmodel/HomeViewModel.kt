package com.kampusdolap.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kampusdolap.app.data.ProductRepository
import com.kampusdolap.app.data.RetrofitClient
import com.kampusdolap.app.model.Product
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = ProductRepository()
    val products = MutableLiveData<List<Product>>()

    fun loadProducts() {
        // Firebase'den kullanıcı ilanlarını çek
        repository.getProducts { firebaseProducts ->

            // Retrofit ile DummyJSON'dan çek
            viewModelScope.launch {
                try {
                    val apiProducts = RetrofitClient.api.getProducts().products.map {
                        Product(
                            id = it.id,
                            title = it.title,
                            price = it.price,
                            imageUrl = it.imageUrl,
                            category = it.category
                        )
                    }
                    // İkisini birleştir
                    products.postValue(apiProducts + firebaseProducts)
                } catch (e: Exception) {
                    products.postValue(firebaseProducts)
                }
            }
        }
    }
}