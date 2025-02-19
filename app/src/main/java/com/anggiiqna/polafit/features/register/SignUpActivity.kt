package com.anggiiqna.polafit.features.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.features.login.LoginActivity
import com.anggiiqna.polafit.databinding.ActivitySignupBinding
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.network.datamodel.RegisterRequest
import com.anggiiqna.polafit.pref.AppPreferences
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var apiService: ApiService
    private lateinit var appPreferences: AppPreferences
    private lateinit var LogInText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        apiService = ApiClient.create()

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    registerUser(name, email, password)
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        LogInText = findViewById(R.id.tv_login_prompt)

        LogInText.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private suspend fun registerUser(name: String, email: String, password: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        try {
            val user = RegisterRequest(username = name, email = email, password = password)
            val response = apiService.register(user)
            appPreferences.saveToken(response.token)
            progressDialog.dismiss()
            goToLogin()
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            progressDialog.dismiss()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
