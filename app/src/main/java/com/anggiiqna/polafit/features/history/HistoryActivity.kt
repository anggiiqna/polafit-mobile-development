package com.anggiiqna.polafit.features.history

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.adapter.FoodHistoryAdapter
import com.anggiiqna.polafit.features.profile.ProfileActivity
import com.anggiiqna.polafit.features.profile.ViewProfile
import com.anggiiqna.polafit.network.datamodel.FoodItem


class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_history)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton: ImageView = findViewById(R.id.icon_back)

        backButton.setOnClickListener {
            val intent = Intent(this@HistoryActivity, ViewProfile::class.java)
            startActivity(intent)
            finish()
        }

        // Sample data - replace with your actual data source
        val foodItems = listOf(
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            ),
            FoodItem(
                "Beef Steaks",
                "16-11-2024",
                "615",
                "2.6",
                "0.0",
                "0.0",
                R.drawable.food
            )

            // Add more items as needed
        )

        // Initialize and set adapter
        adapter = FoodHistoryAdapter(foodItems) { foodItem ->
            // Handle item click: navigate to HistoryDetailActivity
            val intent = Intent(this, HistoryFoodActivity::class.java).apply {
                putExtra("FOOD_NAME", foodItem.name)
                putExtra("FOOD_DATE", foodItem.date)
                putExtra("FOOD_CALORIES", foodItem.calories)
                putExtra("FOOD_PROTEIN", foodItem.protein)
                putExtra("FOOD_CARBS", foodItem.sugar)
                putExtra("FOOD_FAT", foodItem.fiber)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
//
//        // Set up back button click listener
//        findViewById<ImageView>(R.id.icon_back).setOnClickListener {
//            onBackPressed()
//        }
    }
}

