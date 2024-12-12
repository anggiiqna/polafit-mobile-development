package com.anggiiqna.polafit.features.history

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.adapter.FoodHistoryAdapter
import com.anggiiqna.polafit.features.profile.ViewProfile
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.datamodel.FoodItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_history)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton: ImageView = findViewById(R.id.icon_back)

        backButton.setOnClickListener {
            val intent = Intent(this@HistoryActivity, ViewProfile::class.java)
            startActivity(intent)
            finish()
        }

        val userId = 3

        fetchFoodHistory(userId)
    }

    private fun fetchFoodHistory(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.create().getHistoryByUserId(userId.toString())
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                val foodItems = response.map {
                    val createdAtDate = dateFormat.parse(it.createdAt)
                    val formattedDate = createdAtDate?.let { outputDateFormat.format(it) } ?: ""

                    FoodItem(
                        name = it.foodName,
                        date = formattedDate,
                        calories = "${it.calories} Kcal",
                        protein = "${it.protein} Gram",
                        sugar = "${it.sugar} Gram",
                        fiber = "${it.fiber} Gram",
                        imageResource = it.image
                    )
                }

                withContext(Dispatchers.Main) {
                    adapter = FoodHistoryAdapter(foodItems) { foodItem ->
                        val intent = Intent(this@HistoryActivity, HistoryFoodActivity::class.java).apply {
                            putExtra("FOOD_NAME", foodItem.name)
                            putExtra("FOOD_DATE", foodItem.date)
                            putExtra("FOOD_CALORIES", foodItem.calories)
                            putExtra("FOOD_PROTEIN", foodItem.protein)
                            putExtra("FOOD_CARBS", foodItem.sugar)
                            putExtra("FOOD_FAT", foodItem.fiber)
                            putExtra("FOOD_IMAGE", foodItem.imageResource)
                        }
                        startActivity(intent)
                    }
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
