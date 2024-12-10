package com.anggiiqna.polafit.features.profile

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.network.datamodel.UserRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)

        val backButton: ImageView = findViewById(R.id.icon_back)
        usernameEditText = findViewById(R.id.et_username)
        emailEditText = findViewById(R.id.et_email)
        phoneEditText = findViewById(R.id.et_nohanp)
        saveButton = findViewById(R.id.btn_save)

        apiService = ApiClient.create()

        backButton.setOnClickListener {
            finish()
        }

        val userId = intent.getStringExtra("id") ?: ""
        if (userId.isNotEmpty()) {
            fetchUserProfile(userId)
        }

        saveButton.setOnClickListener {
            val updatedUsername = usernameEditText.text.toString()
            val updatedEmail = emailEditText.text.toString()
            val updatedPhone = phoneEditText.text.toString()

            saveUserProfile(userId, updatedUsername, updatedEmail, updatedPhone)
        }
    }

    private fun fetchUserProfile(userId: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getUserById(userId)
                withContext(Dispatchers.Main) {
                    usernameEditText.setText(response.username)
                    emailEditText.setText(response.email)
                    phoneEditText.setText(response.phone)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    usernameEditText.setText("Error")
                    emailEditText.setText("Error")
                    phoneEditText.setText("Error")
                }
            }
        }
    }

    private fun saveUserProfile(userId: String, username: String, email: String, phone: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val userRequest = UserRequest(username, email, phone)
                val response = apiService.updateUserProfile(userId, userRequest)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfileActivity, "Profile updated!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
