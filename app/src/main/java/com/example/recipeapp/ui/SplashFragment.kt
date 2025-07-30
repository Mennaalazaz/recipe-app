package com.example.recipeapp.ui

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeActivity
import com.example.recipeapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Start logo animation (pop + wave combo)
        val combo = AnimationUtils.loadAnimation(requireContext(), R.anim.logo_combo)
        binding.logoText.startAnimation(combo)

        // Fade in sub text
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        fadeIn.duration = 1500
        binding.subText.startAnimation(fadeIn)

        // Show progress bar while splash is active
        binding.progressBar.visibility = View.VISIBLE

        // Navigate after Lottie animation ends
        binding.lottieAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                binding.progressBar.visibility = View.GONE

                val sharedPref = requireActivity().getSharedPreferences("user_prefs", 0)
                val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)

                if (isLoggedIn) {
                    // افتح RecipeActivity اللي فيه BottomNavigation
                    val intent = Intent(requireContext(), RecipeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginOrRegisterFragment)
                }
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
