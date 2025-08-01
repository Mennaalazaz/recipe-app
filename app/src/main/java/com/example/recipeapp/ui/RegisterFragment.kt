package com.example.recipeapp.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.localdb.UserEntity
import com.example.recipeapp.R
import com.example.recipeapp.viewmodel.UserViewModel
import com.example.recipeapp.databinding.FragmentRegisterBinding
import kotlinx.coroutines.launch
import android.content.Intent
import com.example.recipeapp.RecipeActivity

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[UserViewModel::class.java]

        binding.registerButton.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            when {
                name.isEmpty() -> {
                    binding.etName.error = "Name is required"
                    binding.etName.requestFocus()
                }

                email.isEmpty() -> {
                    binding.etEmail.error = "Email is required"
                    binding.etEmail.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.etEmail.error = "Enter a valid email"
                    binding.etEmail.requestFocus()
                }

                password.isEmpty() -> {
                    binding.etPassword.error = "Password is required"
                    binding.etPassword.requestFocus()
                }

                password.length < 6 -> {
                    binding.etPassword.error = "Password must be at least 6 characters"
                    binding.etPassword.requestFocus()
                }

                confirmPassword.isEmpty() -> {
                    binding.etConfirmPassword.error = "Please confirm your password"
                    binding.etConfirmPassword.requestFocus()
                }

                confirmPassword != password -> {
                    binding.etConfirmPassword.error = "Passwords do not match"
                    binding.etConfirmPassword.requestFocus()
                }

                else -> {
                    lifecycleScope.launch {
                        val user = userViewModel.getUserByEmail(email)

                        if (user == null) {
                            // لو مفيش يوزر بنفس الإيميل
                            userViewModel.insertUser(UserEntity(0, name, email, password))

                            val sharedPref =
                                requireActivity().getSharedPreferences("user_prefs", 0)
                            with(sharedPref.edit()) {
                                putString("user_email", email)
                                putBoolean("is_logged_in", true)
                                apply()
                            }

                            // افتح RecipeActivity
                            val intent = Intent(requireContext(), RecipeActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()

                        } else {
                            // لو فيه يوزر بنفس الإيميل
                            Toast.makeText(
                                context,
                                "You already have an account, login please",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                    }
                }
            }
        }

        binding.toLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
