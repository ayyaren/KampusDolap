package com.kampusdolap.app.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kampusdolap.app.R
import com.kampusdolap.app.model.Product

class UserProductAdapter(
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<UserProductAdapter.ViewHolder>() {

    private var items = listOf<Product>()

    fun updateItems(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivUserProductImage)
        val tvTitle: TextView = view.findViewById(R.id.tvUserProductTitle)
        val tvPrice: TextView = view.findViewById(R.id.tvUserProductPrice)
        val tvCategory: TextView = view.findViewById(R.id.tvUserProductCategory)
        val btnDelete: Button = view.findViewById(R.id.btnDeleteProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvPrice.text = "₺${item.price}"
        holder.tvCategory.text = "Kategori: ${item.category}"
        if (!item.imageUrl.isNullOrEmpty()) {
            holder.ivImage.load(item.imageUrl)
        }
        holder.btnDelete.setOnClickListener {
            onDelete(item.id)
        }
    }

    override fun getItemCount() = items.size
}