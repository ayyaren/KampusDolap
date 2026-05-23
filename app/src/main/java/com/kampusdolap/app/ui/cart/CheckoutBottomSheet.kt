package com.kampusdolap.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kampusdolap.app.R
import com.kampusdolap.app.data.AppDatabase
import com.kampusdolap.app.data.NotificationHelper
import com.kampusdolap.app.model.OrderItem
import com.kampusdolap.app.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutBottomSheet(private val product: Product) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvSummary = view.findViewById<TextView>(R.id.tvProductSummary)
        val etAddress = view.findViewById<EditText>(R.id.etAddress)
        val etCity = view.findViewById<EditText>(R.id.etCity)
        val etCardNumber = view.findViewById<EditText>(R.id.etCardNumber)
        val btnComplete = view.findViewById<Button>(R.id.btnCompleteOrder)

        tvSummary.text = "${product.title} — ₺${product.price}"

        btnComplete.setOnClickListener {
            val address = etAddress.text.toString().trim()
            val city = etCity.text.toString().trim()
            val cardNumber = etCardNumber.text.toString().trim()

            if (address.isEmpty() || city.isEmpty() || cardNumber.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cardNumber.length < 16) {
                Toast.makeText(requireContext(), "Geçerli bir kart numarası girin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val order = OrderItem(
                productId = product.id,
                title = product.title,
                price = product.price,
                imageUrl = product.imageUrl,
                size = product.size,
                address = address,
                city = city,
                cardNumber = cardNumber.takeLast(4)
            )

            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getInstance(requireContext()).orderDao().addOrder(order)
            }

            NotificationHelper.sendOrderNotification(requireContext(), product.title)
            Toast.makeText(requireContext(), "Siparişiniz alındı!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}