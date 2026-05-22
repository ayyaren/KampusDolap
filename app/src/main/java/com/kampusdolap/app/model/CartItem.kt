package com.kampusdolap.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey val productId: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val size: String = ""
)