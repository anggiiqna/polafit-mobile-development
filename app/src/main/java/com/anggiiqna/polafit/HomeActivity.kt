package com.anggiiqna.polafit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.network.datamodel.UserResponse
import com.anggiiqna.polafit.pref.AppPreferences
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.anggiiqna.polafit.features.profile.ProfileActivity
import android.widget.ImageView
import android.content.Intent
import android.util.Log

class HomeActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var namaUser: TextView
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        namaUser = findViewById(R.id.namaUser)
        appPreferences = AppPreferences(this)
        apiService = ApiClient.create()

        val profileImageView: ImageView = findViewById(R.id.potouser)
        val id = intent.getStringExtra("id") ?: ""

        profileImageView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        if (id.isNotEmpty()) {
            getUserData(id)
        } else {
            namaUser.text = "User not found"
        }
    }

    private fun getUserData(id: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response: UserResponse = apiService.getUserById(id)
                withContext(Dispatchers.Main) {
                    namaUser.text = response.username
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
