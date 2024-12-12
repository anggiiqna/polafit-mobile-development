package com.anggiiqna.polafit.features.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anggiiqna.polafit.R

class ExerciseAdapter(private val context: Context, private val exerciseNames: List<String>, private val exerciseDescriptions: List<String>, private val exerciseImages: List<Int>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.exerciseName.text = exerciseNames[position]
        holder.exerciseDescription.text = exerciseDescriptions[position]
        holder.exerciseImage.setImageResource(exerciseImages[position])
    }

    override fun getItemCount(): Int {
        return exerciseNames.size
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseImage: ImageView = itemView.findViewById(R.id.exerciseImage)
        val exerciseName: TextView = itemView.findViewById(R.id.exerciseName)
        val exerciseDescription: TextView = itemView.findViewById(R.id.exerciseDescription)
    }
}