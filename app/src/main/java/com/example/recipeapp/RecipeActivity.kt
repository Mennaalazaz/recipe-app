package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.ActivityRecipeBinding
import com.example.recipeapp.ui.FavoriteFragment
import com.example.recipeapp.ui.SearchFragment
import com.example.recipeapp.ui.HomeFragment

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // اول ما يفتح يجيب الـ HomeFragment
        if (savedInstanceState == null) {
            openFragment(HomeFragment())
            binding.bottomNav.selectedItemId = R.id.nav_home
        }

        // Bottom Navigation
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    openFragment(HomeFragment())
                    true
                }
                R.id.nav_favorite -> {
                    openFragment(FavoriteFragment())
                    true
                }
                R.id.nav_search -> {
                    openFragment(SearchFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
