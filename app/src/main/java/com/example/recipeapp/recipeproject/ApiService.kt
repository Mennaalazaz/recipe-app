package com.example.recipeapp.recipeproject

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search.php?s=")
    suspend fun getAllMeals(): RecipeResponse

    // دالة البحث بالـ query
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): RecipeResponse
}
