package com.example.recipeapp.favoritedb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeId: String,
    val title: String,
    val imageUrl: String,
    val instructions: String?,
    val videoUrl: String? = null

)
