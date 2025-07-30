package com.example.recipeapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemFavoriteBinding
import com.example.recipeapp.favoritedb.FavoriteRecipe

class FavoriteAdapter(
    private val onDeleteClick: (FavoriteRecipe) -> Unit,
    private val onItemClick: (FavoriteRecipe) -> Unit
) : ListAdapter<FavoriteRecipe, FavoriteAdapter.FavViewHolder>(DiffCallback) {

    inner class FavViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FavoriteRecipe) {
            // تحميل البيانات في الـ views
            binding.tvTitle.text = recipe.title
            Glide.with(binding.root.context)
                .load(recipe.imageUrl)
                .into(binding.ivImage)

            // أيقونة الحذف
            binding.btnDelete.setImageResource(R.drawable.ic_delete)
            binding.btnDelete.setColorFilter(
                binding.root.context.getColor(android.R.color.holo_red_dark)
            )

            // Action عند الضغط على زرار الحذف
            binding.btnDelete.setOnClickListener {
                onDeleteClick(recipe)
            }

            // 👈 فتح التفاصيل عند الضغط على العنصر
            binding.root.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FavoriteRecipe>() {
            override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe) =
                oldItem.recipeId == newItem.recipeId

            override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe) =
                oldItem == newItem
        }
    }
}
