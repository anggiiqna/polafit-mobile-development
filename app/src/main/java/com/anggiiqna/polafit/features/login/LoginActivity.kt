package com.anggiiqna.polafit.features.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggiiqna.polafit.HomeActivity
import com.anggiiqna.polafit.databinding.ActivityLoginBinding
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.network.datamodel.LoginRequest
import com.anggiiqna.polafit.network.datamodel.RegisterRequest
import com.anggiiqna.polafit.pref.AppPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var apiService: ApiService
    private lateinit var appPreferences: AppPreferences

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        val name = account.email?.substringBefore("@") ?: "Unknown"
                        val email = account.email ?: ""
                        registerWithGoogle(name, email)
                    }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        apiService = ApiClient.create()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginWithEmail(email, password)
        }
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun registerWithGoogle(name: String, email: String) {
        lifecycleScope.launch {
            var isError: Boolean
            try {
                val response = apiService.register(
                    RegisterRequest(
                        username = name,
                        email = email,
                        password = ""
                    )
                )
                appPreferences.saveToken(response.token)
                isError = false
            } catch (ex: Exception) {
                Toast.makeText(this@LoginActivity, "Login error: ${ex.message}", Toast.LENGTH_SHORT).show()
                isError = true
            }

            if (!isError) {
                goToHome()
                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.login(
                    LoginRequest(
                        username = email,
                        password = password
                    )
                )
                appPreferences.saveToken(response.token)
                goToHome()
                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(this@LoginActivity, "Login error: ${ex.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}