package com.anggiiqna.polafit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.pref.AppPreferences
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.anggiiqna.polafit.features.profile.ViewProfile
import android.widget.ImageView
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.anggiiqna.polafit.databinding.ActivityHomeBinding
import com.anggiiqna.polafit.features.scan.ScanActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anggiiqna.polafit.features.inputdata.InputActivity
import com.anggiiqna.polafit.network.datamodel.UserResponse
import com.anggiiqna.polafit.features.adapter.FoodAdapter
import com.anggiiqna.polafit.features.adapter.ExerciseAdapter


class HomeActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var namaUser: TextView
    private lateinit var appPreferences: AppPreferences
    private lateinit var profileImageView: ImageView
    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        namaUser = homeBinding.namaUser
        profileImageView = homeBinding.potouser
        appPreferences = AppPreferences(this)
        apiService = ApiClient.create()

        val id = intent.getStringExtra("id") ?: ""

        profileImageView.setOnClickListener {
            val intent = Intent(this, ViewProfile::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        if (id.isNotEmpty()) {
            getUserData(id)
        } else {
            namaUser.text = "User not found"
        }

        val bottomNavigationView: BottomNavigationView = homeBinding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_input -> {
                    val intent = Intent(this, InputActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_scan -> {
                    val intent = Intent(this, ScanActivity::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val foodNames = resources.getStringArray(R.array.food_name).toList()
        val foodDescriptions = resources.getStringArray(R.array.food_description).toList()
        val foodImages = listOf(
            R.drawable.chickencurry, R.drawable.chickenwings, R.drawable.chocolatecake, R.drawable.donuts, R.drawable.dumplings,
            R.drawable.frenchfries, R.drawable.friedcalamari, R.drawable.friedrice, R.drawable.hamburger, R.drawable.icecream,
            R.drawable.padthai, R.drawable.pancakes, R.drawable.pizza, R.drawable.ramen, R.drawable.spaghettibolognese,
            R.drawable.springrolls, R.drawable.sushi, R.drawable.takoyaki, R.drawable.tiramisu, R.drawable.waffles
        )

        val recyclerViewFood = findViewById<RecyclerView>(R.id.foodRecyclerView)
        recyclerViewFood.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewFood.adapter = FoodAdapter(this, foodNames, foodDescriptions, foodImages)

        val exerciseNames = resources.getStringArray(R.array.exercise_name).toList()
        val exerciseDescriptions = resources.getStringArray(R.array.exercise_description).toList()
        val exerciseImages = listOf(
            R.drawable.running, R.drawable.cycling, R.drawable.aerobics, R.drawable.weightlifting
        )

        val recyclerViewExercise = findViewById<RecyclerView>(R.id.exerciseRecyclerView)
        recyclerViewExercise.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewExercise.adapter = ExerciseAdapter(this, exerciseNames, exerciseDescriptions, exerciseImages)
    }


    private fun getUserData(id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response: UserResponse = apiService.getUserById(id)
                withContext(Dispatchers.Main) {
                    namaUser.text = response.username
                    Glide.with(this@HomeActivity)
                        .load(if (response.image.isNullOrEmpty()) R.drawable.noprofile else response.image)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profileImageView)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    namaUser.text = "Error loading user data"
                }
            }
        }
    }
}