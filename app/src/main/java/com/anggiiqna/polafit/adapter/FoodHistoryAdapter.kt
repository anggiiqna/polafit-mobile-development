package com.anggiiqna.polafit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.network.datamodel.FoodItem
import com.bumptech.glide.Glide

class FoodHistoryAdapter(private val foodItems: List<FoodItem>, private val onItemClick: (FoodItem) -> Unit) :
    RecyclerView.Adapter<FoodHistoryAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.food_image)
        val foodName: TextView = itemView.findViewById(R.id.food_name)
        val foodDate: TextView = itemView.findViewById(R.id.food_date)
        val calories: TextView = itemView.findViewById(R.id.calories_value)
        val protein: TextView = itemView.findViewById(R.id.protein_value)
        val sugar: TextView = itemView.findViewById(R.id.sugar_value)
        val fiber: TextView = itemView.findViewById(R.id.fiber_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food_history, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = foodItems[position]
        holder.foodName.text = item.name
        holder.foodDate.text = item.date
        holder.calories.text = item.calories
        holder.protein.text = item.protein
        holder.sugar.text = item.sugar
        holder.fiber.text = item.fiber

        Glide.with(holder.itemView.context)
            .load(item.imageResource)
            .into(holder.foodImage)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
    override fun getItemCount() = foodItems.size
}