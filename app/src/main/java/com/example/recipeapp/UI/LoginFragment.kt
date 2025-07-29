package com.example.recipeapplication.UI

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
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentLoginBinding
import com.example.recipeapplication.ViewModel.UserViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private  var _binding: FragmentLoginBinding?= null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[UserViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

           when {
               email.isEmpty() -> {
                   binding.loginEmail.error = "Email is required"
                   binding.loginEmail.requestFocus()
               }
               password.isEmpty() -> {
                   binding.loginPassword.error = "Password is required"
                   binding.loginPassword.requestFocus()
               }
               password.length < 6 -> {
                   binding.loginPassword.error = "Password must be at least 6 characters"
                   binding.loginPassword.requestFocus()
               }
               !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                   binding.loginEmail.error = "Enter a valid email"
                   binding.loginEmail.requestFocus()
               }
               email.isNotEmpty() && password.isNotEmpty() -> {
                   lifecycleScope.launch {
                       val user = userViewModel.getUserByEmailAndPassword(email, password)
                       if (user == null) {
                           Toast.makeText(context, "You don't have an account, Register please", Toast.LENGTH_LONG).show()
                           findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                       } else {
                           val sharedPref = requireActivity().getSharedPreferences("user_prefs", 0)
                           with(sharedPref.edit()) {
                               putString("user_email", email)
                               putBoolean("is_logged_in", true)
                               apply()
                           }
                           findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                       }
                   }


               }

               }
           }

        binding.toRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        }
}
