package com.example.recipeproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeVM : ViewModel() {
    private val repository = RecipeRepo(ApiClient.apiService)

    private val _meals = MutableLiveData<List<Recipe>>()
    val meals: LiveData<List<Recipe>> get() = _meals

    init {
        viewModelScope.launch {
            try {
                val response = repository.getAllMeals()
                _meals.value = response.meals
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error: ${e.message}")
            }
        }
    }
}
