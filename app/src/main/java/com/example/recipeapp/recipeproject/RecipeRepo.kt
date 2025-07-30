package com.example.recipeapp.recipeproject


class RecipeRepo (private val apiService: ApiService) {
    suspend fun getAllMeals(): RecipeResponse {
        return apiService.getAllMeals()
    }

}