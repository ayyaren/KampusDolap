package com.kampusdolap.app.data

import com.google.firebase.firestore.FirebaseFirestore
import com.kampusdolap.app.model.Product

class ProductRepository {
    private val firestore = FirebaseFirestore.getInstance()

    fun getProducts(onResult: (List<Product>) -> Unit) {
        firestore.collection("products")
            .addSnapshotListener { snapshot, _ ->
                val products = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Product::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                onResult(products)
            }
    }

    fun uploadProduct(product: Product, onResult: (Boolean) -> Unit) {
        val docRef = firestore.collection("products").document()
        val productWithId = product.copy(id = docRef.id)
        docRef.set(productWithId)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}