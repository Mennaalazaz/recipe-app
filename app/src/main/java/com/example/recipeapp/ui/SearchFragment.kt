package com.example.recipeapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.recipeproject.ApiClient
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // إعداد الـ Adapter مع الضغط على العنصر
        adapter = SearchAdapter { recipe ->
            val bundle = Bundle().apply {
                putString("idMeal", recipe.idMeal)
                putString("strMeal", recipe.strMeal)
                putString("strMealThumb", recipe.strMealThumb)
                putString("strInstructions", recipe.strInstructions ?: "No instructions available")
                putString("strYoutube", recipe.strYoutube ?: "")
            }

            val detailFragment = RecipeDetailFragment().apply {
                arguments = bundle
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSearch.adapter = adapter

        // زر البحث
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchRecipes(query)
            } else {
                binding.etSearch.error = "Please enter a search term"
            }
        }

        // تحميل مقترحات أول مرة
        if (binding.etSearch.text.isNullOrEmpty()) {
            loadSuggestedRecipes()
        }

        // البحث أثناء الكتابة
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    searchRecipes(query)
                } else {
                    loadSuggestedRecipes()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val query = binding.etSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            searchRecipes(query)
        } else {
            loadSuggestedRecipes()
        }
    }

    private fun searchRecipes(query: String) {
        val api = ApiClient.apiService

        lifecycleScope.launch {
            try {
                val response = api.searchMeals(query)
                val recipes = response.meals ?: emptyList()
                adapter.submitList(recipes)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadSuggestedRecipes() {
        val api = ApiClient.apiService

        lifecycleScope.launch {
            try {
                val response = api.searchMeals("a")
                val recipes = response.meals ?: emptyList()
                adapter.submitList(recipes)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
