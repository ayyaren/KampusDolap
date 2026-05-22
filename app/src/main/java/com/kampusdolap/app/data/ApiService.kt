package com.kampusdolap.app.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class ApiProduct(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,
    @SerializedName("thumbnail") val imageUrl: String = "",
    val category: String = "",
    val description: String = ""
)

data class ProductResponse(
    val products: List<ApiProduct>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

interface ApiService {
    @GET("products?limit=30")
    suspend fun getProducts(): ProductResponse
}