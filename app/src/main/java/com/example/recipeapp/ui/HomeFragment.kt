package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.recipeproject.RecipeAdapter
import com.example.recipeapp.recipeproject.RecipeVM

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecipeVM
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // إعداد الـ Adapter مع الانتقال إلى RecipeDetailFragment
        adapter = RecipeAdapter { meal ->
            val bundle = Bundle().apply {
                putString("idMeal", meal.idMeal)
                putString("strMeal", meal.strMeal)          // العنوان
                putString("strMealThumb", meal.strMealThumb) // الصورة
            }

            val detailFragment = RecipeDetailFragment().apply {
                arguments = bundle
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // إعداد الـ ViewModel
        viewModel = ViewModelProvider(this)[RecipeVM::class.java]
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.submitList(meals)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
