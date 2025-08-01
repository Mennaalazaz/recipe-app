package com.example.recipeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R

data class Creator(val name: String)

class CreatorsAdapter(private val creators: List<Creator>) :
    RecyclerView.Adapter<CreatorsAdapter.CreatorViewHolder>() {

    class CreatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val creatorName: TextView = itemView.findViewById(R.id.creatorName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_creator, parent, false)
        return CreatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        val creator = creators[position]
        holder.creatorName.text = creator.name
    }

    override fun getItemCount() = creators.size
}
