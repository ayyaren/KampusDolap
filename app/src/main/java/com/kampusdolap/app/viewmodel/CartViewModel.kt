package com.kampusdolap.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kampusdolap.app.data.AppDatabase
import com.kampusdolap.app.model.CartItem

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartDao = AppDatabase.getInstance(application).cartDao()

    val cartItems: LiveData<List<CartItem>> = cartDao.getCartItems()

    suspend fun addToCart(item: CartItem) {
        cartDao.addToCart(item)
    }

    suspend fun removeFromCart(item: CartItem) {
        cartDao.removeFromCart(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}