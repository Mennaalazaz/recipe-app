package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentLoginOrRegisterBinding

class LoginOrRegisterFragment : Fragment() {

    private var _binding: FragmentLoginOrRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginOrRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Animation
        binding.image.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        )

        binding.chefImg.startAnimation(
            AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        )

        // التنقل للـ Login و Register
        binding.logintext.setOnClickListener {
            findNavController().navigate(R.id.action_loginOrRegisterFragment_to_loginFragment)
        }

        binding.registertext.setOnClickListener {
            findNavController().navigate(R.id.action_loginOrRegisterFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
