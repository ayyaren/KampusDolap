package com.kampusdolap.app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kampusdolap.app.R
import com.kampusdolap.app.viewmodel.CartViewModel

class CartFragment : Fragment() {

    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            val tvEmpty = view.findViewById<TextView>(R.id.tvCartEmpty)
            if (items.isEmpty()) {
                tvEmpty?.visibility = View.VISIBLE
            } else {
                tvEmpty?.visibility = View.GONE
            }
        }
    }
}