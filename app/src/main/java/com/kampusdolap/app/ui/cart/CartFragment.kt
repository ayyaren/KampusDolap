package com.kampusdolap.app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kampusdolap.app.R
import com.kampusdolap.app.data.AppDatabase
import androidx.lifecycle.Observer

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvCart = view.findViewById<RecyclerView>(R.id.rvCart)
        val tvEmpty = view.findViewById<TextView>(R.id.tvCartEmpty)

        val adapter = CartAdapter()
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        rvCart.adapter = adapter

        AppDatabase.getInstance(requireContext()).orderDao().getOrders()
            .observe(viewLifecycleOwner, Observer { orders ->
                adapter.updateItems(orders)
                if (orders.isEmpty()) {
                    tvEmpty.visibility = View.VISIBLE
                    rvCart.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    rvCart.visibility = View.VISIBLE
                }
            })
    }
}