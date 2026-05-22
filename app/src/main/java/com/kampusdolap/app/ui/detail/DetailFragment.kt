package com.kampusdolap.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.kampusdolap.app.data.AppDatabase
import com.kampusdolap.app.databinding.FragmentDetailBinding
import com.kampusdolap.app.model.CartItem
import com.kampusdolap.app.model.FavoriteItem
import com.kampusdolap.app.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getSerializable("product") as? Product

        product?.let {
            binding.tvTitle.text = it.title
            binding.tvPrice.text = "₺${it.price}"
            binding.tvCategory.text = "Kategori: ${it.category}"
            binding.tvSize.text = "Beden: ${it.size}"
            binding.tvSellerCity.text = "Şehir: ${it.sellerCity}"
            binding.tvDescription.text = it.description

            binding.btnAddToCart.setOnClickListener { _ ->
                val cartItem = CartItem(
                    productId = it.id,
                    title = it.title,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    size = it.size
                )
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.getInstance(requireContext()).cartDao().addToCart(cartItem)
                }
                Toast.makeText(requireContext(), "Sepete eklendi!", Toast.LENGTH_SHORT).show()
            }

            binding.btnFavorite.setOnClickListener { _ ->
                val favoriteItem = FavoriteItem(
                    productId = it.id,
                    title = it.title,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    size = it.size
                )
                CoroutineScope(Dispatchers.IO).launch {
                    AppDatabase.getInstance(requireContext()).favoritesDao().addFavorite(favoriteItem)
                }
                Toast.makeText(requireContext(), "Favorilere eklendi!", Toast.LENGTH_SHORT).show()
            }

            binding.btnBuy.setOnClickListener { _ ->
                Toast.makeText(requireContext(), "Siparişiniz alındı! 🎉", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}