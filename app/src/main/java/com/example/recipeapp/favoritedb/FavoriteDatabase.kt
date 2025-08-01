package com.example.recipeapp.favoritedb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.favoritedb.FavoriteRecipe

@Database(entities = [FavoriteRecipe::class], version = 4, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile private var instance: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "recipes_db"
                ).build().also { instance = it }
            }
        }
    }
}
