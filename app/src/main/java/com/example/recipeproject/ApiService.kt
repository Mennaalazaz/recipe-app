package com.example.recipeproject
import retrofit2.http.GET

interface ApiService {
    @GET("search.php?f=a")
    suspend fun getMeals(): RecipeResponse
}