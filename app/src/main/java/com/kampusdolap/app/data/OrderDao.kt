package com.kampusdolap.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kampusdolap.app.model.OrderItem

@Dao
interface OrderDao {
    @Insert
    suspend fun addOrder(item: OrderItem)

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getOrders(): LiveData<List<OrderItem>>
}