package com.kampusdolap.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kampusdolap.app.model.CartItem

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(item: CartItem)

    @Delete
    suspend fun removeFromCart(item: CartItem)

    @Query("SELECT * FROM cart")
    fun getCartItems(): LiveData<List<CartItem>>

    @Query("DELETE FROM cart")
    suspend fun clearCart()
}