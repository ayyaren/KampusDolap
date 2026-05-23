package com.kampusdolap.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kampusdolap.app.R
import com.kampusdolap.app.databinding.FragmentProfileBinding
import com.kampusdolap.app.ui.cart.CartAdapter
import com.kampusdolap.app.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // İlanlarım adapter
        val productAdapter = UserProductAdapter { productId ->
            viewModel.deleteProduct(productId)
        }
        binding.rvUserProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserProducts.adapter = productAdapter

        // Satın Aldıklarım adapter
        val orderAdapter = CartAdapter()
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = orderAdapter

        // Kullanıcı adı
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvUserName.text = name
        }

        // Email
        viewModel.userEmail.observe(viewLifecycleOwner) { email ->
            binding.tvUserEmail.text = email
        }

        // İlanlarım
        viewModel.userProducts.observe(viewLifecycleOwner) { products ->
            productAdapter.updateItems(products)
            binding.tvProductCount.text = products.size.toString()
            if (products.isEmpty()) {
                binding.tvNoProducts.visibility = View.VISIBLE
                binding.rvUserProducts.visibility = View.GONE
            } else {
                binding.tvNoProducts.visibility = View.GONE
                binding.rvUserProducts.visibility = View.VISIBLE
            }
        }

        // Satın Aldıklarım
        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            orderAdapter.updateItems(orders)
            binding.tvOrderCount.text = orders.size.toString()
            if (orders.isEmpty()) {
                binding.tvNoOrders.visibility = View.VISIBLE
                binding.rvOrders.visibility = View.GONE
            } else {
                binding.tvNoOrders.visibility = View.GONE
                binding.rvOrders.visibility = View.VISIBLE
            }
        }

        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(R.id.addProductFragment)
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.loadUserProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}