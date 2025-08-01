package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.FavoriteViewModel
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // تمرير كولباكين: الحذف وفتح التفاصيل
        adapter = FavoriteAdapter(
            onDeleteClick = { recipe ->
                viewModel.deleteById(recipe.recipeId)
            },
            onItemClick = { recipe ->
                // فتح صفحة التفاصيل عند الضغط على العنصر
                val bundle = Bundle().apply {
                    putString("idMeal", recipe.recipeId)
                    putString("strMeal", recipe.title)
                    putString("strMealThumb", recipe.imageUrl)
                    putString("strInstructions", recipe.instructions ?: "No instructions available")
                    putString("strYoutube", recipe.videoUrl ?: "")

                }
                val detailFragment = RecipeDetailFragment().apply {
                    arguments = bundle
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorites.adapter = adapter

        // ملاحظة البيانات المفضلة
        viewModel.allFavorites.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
