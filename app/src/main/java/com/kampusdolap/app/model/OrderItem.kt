package com.kampusdolap.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val size: String = "",
    val address: String = "",
    val city: String = "",
    val cardNumber: String = "",
    val orderDate: Long = System.currentTimeMillis()
)