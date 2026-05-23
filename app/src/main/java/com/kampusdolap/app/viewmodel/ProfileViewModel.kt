package com.kampusdolap.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kampusdolap.app.model.Product

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userProducts = MutableLiveData<List<Product>>()
    val userProducts: LiveData<List<Product>> = _userProducts

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    init {
        loadUserEmail()
        loadUserProducts()
    }

    private fun loadUserEmail() {
        val email = auth.currentUser?.email ?: "Kullanıcı"
        _userEmail.value = email
    }

    fun loadUserProducts() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("products")
            .whereEqualTo("sellerId", userId)
            .addSnapshotListener { snapshot, _ ->
                val products = snapshot?.toObjects(Product::class.java) ?: emptyList()
                _userProducts.postValue(products)
            }
    }
}