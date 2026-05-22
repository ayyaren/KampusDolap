package com.kampusdolap.app.data

import com.kampusdolap.app.model.Product
import retrofit2.http.GET

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

interface ApiService {
    @GET("products?limit=30")
    suspend fun getProducts(): ProductResponse
}