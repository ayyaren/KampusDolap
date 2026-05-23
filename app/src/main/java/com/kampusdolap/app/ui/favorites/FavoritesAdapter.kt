package com.kampusdolap.app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kampusdolap.app.R
import com.kampusdolap.app.model.FavoriteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesAdapter(
    private val onRemove: suspend (FavoriteItem) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private var items = listOf<FavoriteItem>()

    fun updateItems(newItems: List<FavoriteItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivFavoriteImage)
        val tvTitle: TextView = view.findViewById(R.id.tvFavoriteTitle)
        val tvPrice: TextView = view.findViewById(R.id.tvFavoritePrice)
        val tvSize: TextView = view.findViewById(R.id.tvFavoriteSize)
        val btnRemove: Button = view.findViewById(R.id.btnRemoveFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvPrice.text = "₺${item.price}"
        holder.tvSize.text = "Beden: ${item.size}"
        if (!item.imageUrl.isNullOrEmpty()) {
            holder.ivImage.load(item.imageUrl)
        }
        holder.btnRemove.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                onRemove(item)
            }
        }
    }

    override fun getItemCount() = items.size
}