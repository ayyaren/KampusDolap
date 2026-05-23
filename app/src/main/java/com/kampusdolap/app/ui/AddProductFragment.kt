package com.kampusdolap.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kampusdolap.app.data.ProductRepository
import com.kampusdolap.app.databinding.FragmentAddProductBinding
import com.kampusdolap.app.model.Product
import androidx.navigation.fragment.findNavController
import com.kampusdolap.app.R

class AddProductFragment : Fragment() {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val repository = ProductRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val price = binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val category = binding.etCategory.text.toString().trim()
            val size = binding.etSize.text.toString().trim()
            val city = binding.etCity.text.toString().trim()
            val imageUrl = binding.etImageUrl.text.toString().trim()

            if (title.isEmpty() || category.isEmpty() || city.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sellerId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val product = Product(
                title = title,
                price = price,
                category = category,
                size = size,
                sellerCity = city,
                sellerId = sellerId,
                imageUrl = imageUrl
            )

            repository.uploadProduct(product) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Ürün eklendi!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeFragment)


                } else {
                    Toast.makeText(requireContext(), "Hata oluştu!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}