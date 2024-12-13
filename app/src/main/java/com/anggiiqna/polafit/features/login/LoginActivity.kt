package com.anggiiqna.polafit.features.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggiiqna.polafit.HomeActivity
import com.anggiiqna.polafit.MainActivity
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.databinding.ActivityLoginBinding
import com.anggiiqna.polafit.features.history.HistoryActivity
import com.anggiiqna.polafit.features.register.SignUpActivity
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.network.datamodel.LoginRequest
import com.anggiiqna.polafit.pref.AppPreferences
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: ApiService
    private lateinit var appPreferences: AppPreferences
    private lateinit var SignUpText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        apiService = ApiClient.create()

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            loginWithUsername(username, password)
        }
        SignUpText = findViewById(R.id.tv_signupclick)

        SignUpText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun loginWithUsername(username: String, password: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        lifecycleScope.launch {
            try {
                val response = apiService.login(
                    LoginRequest(
                        username = username,
                        password = password
                    )
                )
                appPreferences.saveToken(response.token)

                val id = response.user.id
                val token = response.token

                Log.d("LoginActivity", "Token saved: $token")
                progressDialog.dismiss()
                goToHome(id)
                isToken(token)

                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                progressDialog.dismiss()
                Toast.makeText(this@LoginActivity, "Login error: ${ex.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goToHome(id: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("id", id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun isToken(token: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("token", token)
    }
}