package com.kampusdolap.app.data

import com.google.firebase.firestore.FirebaseFirestore
import com.kampusdolap.app.model.Product

class ProductRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getProducts(onResult: (List<Product>) -> Unit) {
        firestore.collection("products")
            .addSnapshotListener { snapshot, _ ->
                val products = snapshot?.toObjects(Product::class.java) ?: emptyList()
                onResult(products)
            }
    }

    fun uploadProduct(product: Product, onResult: (Boolean) -> Unit) {
        firestore.collection("products")
            .add(product)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}