package com.kampusdolap.app.model

data class Product(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val category: String = "",
    val size: String = "",
    val sellerCity: String = "",
    val sellerId: String = "",
    val description: String = ""
)