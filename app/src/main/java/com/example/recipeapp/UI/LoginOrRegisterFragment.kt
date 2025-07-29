package com.example.recipeapplication.UI

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
    private  var _binding: FragmentLoginOrRegisterBinding?= null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginOrRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val image = binding.image
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        image.startAnimation(animation)


        val Chefimage = binding.chefImg
        val animation2 = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        Chefimage.startAnimation(animation2)

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