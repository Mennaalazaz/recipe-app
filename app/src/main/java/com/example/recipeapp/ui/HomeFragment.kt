package com.example.recipeapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // تفعيل المنيو
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ربط التولبار
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // إعداد الـ Adapter
        adapter = RecipeAdapter { meal ->
            val bundle = Bundle().apply {
                putString("idMeal", meal.idMeal)
                putString("strMeal", meal.strMeal)
                putString("strMealThumb", meal.strMealThumb)
                putString("strInstructions", meal.strInstructions) // ✅ أضفنا الوصف
                putString("strYoutube", meal.strYoutube)

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

        // ViewModel
        viewModel = ViewModelProvider(this)[RecipeVM::class.java]
        viewModel.meals.observe(viewLifecycleOwner) { meals ->
            adapter.submitList(meals)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about_creator -> {
                startActivity(Intent(requireContext(), CreatorsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
