package com.anggiiqna.polafit.features.history

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.R
import com.bumptech.glide.Glide

class HistoryFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_food_history)

        val foodName = intent.getStringExtra("FOOD_NAME")
        val foodDate = intent.getStringExtra("FOOD_DATE")
        val foodCalories = intent.getStringExtra("FOOD_CALORIES")
        val foodProtein = intent.getStringExtra("FOOD_PROTEIN")
        val foodCarbs = intent.getStringExtra("FOOD_CARBS")
        val foodFat = intent.getStringExtra("FOOD_FAT")
        val imageResource = intent.getStringExtra("FOOD_IMAGE")
        val backButton: ImageView = findViewById(R.id.icon_back)

        val id = intent.getStringExtra("id")

        backButton.setOnClickListener {
            val intent = Intent(this@HistoryFoodActivity, HistoryActivity::class.java)
            intent.putExtra("id", id)
            finish()
        }

        findViewById<TextView>(R.id.name_food_result).text = foodName
        findViewById<TextView>(R.id.calories_text_view).text = foodCalories
        findViewById<TextView>(R.id.protein_text_view).text = foodProtein
        findViewById<TextView>(R.id.carbo_text_view).text = foodCarbs
        findViewById<TextView>(R.id.fat_text_view).text = foodFat
        Glide.with(this)
            .load(imageResource)
            .into(findViewById<ImageView>(R.id.food_image))
    }
}


