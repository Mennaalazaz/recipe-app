package com.example.recipeproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide

class RecipeAdapter(private val onClick: (Recipe) -> Unit) :
    ListAdapter<Recipe, RecipeAdapter.MealViewHolder>(DiffCallback()) {

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealName: TextView = itemView.findViewById(R.id.textMealName)
        private val mealThumb: ImageView = itemView.findViewById(R.id.imageMealThumb)

        fun bind(recipe: Recipe, onClick: (Recipe) -> Unit) {
            mealName.text = recipe.strMeal
            Glide.with(itemView.context).load(recipe.strMealThumb).into(mealThumb)
            itemView.setOnClickListener { onClick(recipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem.idMeal == newItem.idMeal
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem
    }
}
