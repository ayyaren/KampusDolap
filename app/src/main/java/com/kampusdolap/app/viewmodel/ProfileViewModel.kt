package com.kampusdolap.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kampusdolap.app.data.AppDatabase
import com.kampusdolap.app.model.OrderItem
import com.kampusdolap.app.model.Product

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val orderDao = AppDatabase.getInstance(application).orderDao()

    private val _userProducts = MutableLiveData<List<Product>>()
    val userProducts: LiveData<List<Product>> = _userProducts

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    val orders: LiveData<List<OrderItem>> = orderDao.getOrders()

    init {
        loadUserInfo()
        loadUserProducts()
    }

    private fun loadUserInfo() {
        val uid = auth.currentUser?.uid ?: return
        _userEmail.value = auth.currentUser?.email ?: ""
        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                _userName.value = doc.getString("name") ?: ""
            }
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

    fun deleteProduct(productId: String) {
        if (productId.isNotEmpty()) {
            firestore.collection("products").document(productId).delete()
        }
    }
}