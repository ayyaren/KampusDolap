package com.kampusdolap.app.ui.detail
import com.kampusdolap.app.data.NotificationHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kampusdolap.app.R
import com.kampusdolap.app.data.AppDatabase
import com.kampusdolap.app.databinding.FragmentDetailBinding
import com.kampusdolap.app.model.CartItem
import com.kampusdolap.app.model.FavoriteItem
import com.kampusdolap.app.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import coil.load

class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var googleMap: GoogleMap? = null
    private var sellerCity: String = ""

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

        val product = if (android.os.Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("product", Product::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("product")
        }
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        product?.let {
            sellerCity = it.sellerCity ?: ""
            if (!it.imageUrl.isNullOrEmpty()) {
                binding.ivProductImage.load(it.imageUrl)
            }
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

            binding.btnBuy.setOnClickListener {
                NotificationHelper.sendOrderNotification(requireContext(), product.title)
                Toast.makeText(requireContext(), "Siparişiniz alındı!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val cityCoordinates = getCityCoordinates(sellerCity)
        cityCoordinates?.let {
            googleMap?.addMarker(MarkerOptions().position(it).title(sellerCity))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
        }
    }

    private fun getCityCoordinates(city: String): LatLng? {
        return when (city.lowercase()) {
            "istanbul" -> LatLng(41.0082, 28.9784)
            "ankara" -> LatLng(39.9334, 32.8597)
            "izmir" -> LatLng(38.4192, 27.1287)
            "bursa" -> LatLng(40.1885, 29.0610)
            "antalya" -> LatLng(36.8969, 30.7133)
            "adana" -> LatLng(37.0000, 35.3213)
            "konya" -> LatLng(37.8746, 32.4932)
            "gaziantep" -> LatLng(37.0662, 37.3833)
            "trabzon" -> LatLng(41.0015, 39.7178)
            "kayseri" -> LatLng(38.7312, 35.4787)
            else -> LatLng(39.9334, 32.8597)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}