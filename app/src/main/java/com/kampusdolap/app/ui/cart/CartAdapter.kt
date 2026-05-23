package com.kampusdolap.app.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kampusdolap.app.R
import com.kampusdolap.app.model.OrderItem

class CartAdapter : RecyclerView.Adapter<CartAdapter.OrderViewHolder>() {

    private var items = listOf<OrderItem>()

    fun updateItems(newItems: List<OrderItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivCartImage)
        val tvTitle: TextView = view.findViewById(R.id.tvCartTitle)
        val tvPrice: TextView = view.findViewById(R.id.tvCartPrice)
        val tvSize: TextView = view.findViewById(R.id.tvCartSize)
        val tvAddress: TextView = view.findViewById(R.id.tvCartAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvPrice.text = "₺${item.price}"
        holder.tvSize.text = "Beden: ${item.size}"
        holder.tvAddress.text = "📍 ${item.city}, ${item.address}"
        if (!item.imageUrl.isNullOrEmpty()) {
            holder.ivImage.load(item.imageUrl)
        }
    }

    override fun getItemCount() = items.size
}