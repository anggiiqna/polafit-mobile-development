package com.anggiiqna.polafit.features.scan

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.features.history.HistoryActivity
import com.anggiiqna.polafit.features.history.HistoryFoodActivity

class ScanResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)

        val nameFoodResultTextView: TextView = findViewById(R.id.name_food_result)
        val servingSizeResultTextView: TextView = findViewById(R.id.serving_size_result)
        val caloriesTextView: TextView = findViewById(R.id.calories_text_view)
        val proteinTextView: TextView = findViewById(R.id.protein_text_view)
        val fatTextView: TextView = findViewById(R.id.fat_text_view)
        val carboTextView: TextView = findViewById(R.id.carbo_text_view)
        val fiberTextView: TextView = findViewById(R.id.fiber_text_view)
        val sugarTextView: TextView = findViewById(R.id.sugar_text_view)
        val foodImageView: ImageView = findViewById(R.id.food_image)
        val backButton: ImageView = findViewById(R.id.icon_back)
        val saveButton: Button = findViewById(R.id.btnResult)

        backButton.setOnClickListener {
            val intent = Intent(this@ScanResultActivity, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }

        val foodName = intent.getStringExtra("Makanan")
        val servingSize = "${intent.getStringExtra("Berat_per_Serving") ?: ""} Gram"
        val calories = "${intent.getStringExtra("Kalori") ?: ""} Kcal"
        val protein = "${intent.getStringExtra("Protein") ?: ""} Gram"
        val fat = "${intent.getStringExtra("Lemak") ?: ""} Gram"
        val carbo = "${intent.getStringExtra("Karbohidrat") ?: ""} Gram"
        val fiber = "${intent.getStringExtra("Serat") ?: ""} Gram"
        val sugar = "${intent.getStringExtra("Gula") ?: ""} Gram"

        val imageUriString = intent.getStringExtra("ImageUri")



        // Set the name to the TextView
        nameFoodResultTextView.text = foodName
        servingSizeResultTextView.text = servingSize
        caloriesTextView.text = calories
        proteinTextView.text = protein
        fatTextView.text = fat
        carboTextView.text = carbo
        fiberTextView.text = fiber
        sugarTextView.text = sugar


        imageUriString?.let { uriString ->
            val imageUri = Uri.parse(uriString)
            foodImageView.setImageURI(imageUri)
        }

        // Handle save button click
        saveButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("FOOD_NAME", foodName)
                putExtra("FOOD_DATE", "12-12-2024") // Example date, you can use dynamic data
                putExtra("FOOD_CALORIES", calories)
                putExtra("FOOD_PROTEIN", protein)
                putExtra("FOOD_CARBS", carbo)
                putExtra("FOOD_FAT", fat)
                putExtra("FOOD_IMAGE", imageUriString)
            }
            startActivity(intent)
        }
    }
}


