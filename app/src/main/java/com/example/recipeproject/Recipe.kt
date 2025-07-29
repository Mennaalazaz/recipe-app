package com.example.recipeproject

data class Recipe(
    val idMeal : String,
    val strMeal : String,
    val strCategory : String,
    val strArea : String,
    val strMealThumb : String,
    val strInstructions : String
)
data class RecipeResponse(val meals: List<Recipe>)
