package com.kampusdolap.app.ui.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.kampusdolap.app.R
import com.kampusdolap.app.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val university = binding.etUniversity.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Boş alan kontrolü
            if (name.isEmpty() || university.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Email format kontrolü
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Geçerli bir e-posta adresi girin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Şifre uzunluk kontrolü
            if (password.length < 6) {
                Toast.makeText(requireContext(), "Şifre en az 6 karakter olmalı!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val userId = result.user?.uid ?: return@addOnSuccessListener
                    val userMap = hashMapOf(
                        "name" to name,
                        "university" to university,
                        "email" to email,
                        "userId" to userId
                    )
                    firestore.collection("users").document(userId)
                        .set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Kullanıcı bilgileri kaydedilemedi!", Toast.LENGTH_LONG).show()
                        }
                }
                .addOnFailureListener { e ->
                    val mesaj = when (e) {
                        is FirebaseAuthWeakPasswordException -> "Şifre çok zayıf, daha güçlü bir şifre seçin!"
                        is FirebaseAuthInvalidCredentialsException -> "Geçersiz e-posta adresi!"
                        is FirebaseAuthUserCollisionException -> "Bu e-posta adresi zaten kullanımda!"
                        else -> "Kayıt başarısız: ${e.message}"
                    }
                    Toast.makeText(requireContext(), mesaj, Toast.LENGTH_LONG).show()
                }
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}