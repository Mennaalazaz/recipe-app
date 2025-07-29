package com.example.recipeproject


class RecipeRepo (private val apiService: ApiService) {
    suspend fun getAllMeals(): RecipeResponse {
        return apiService.getMeals()
    }
}