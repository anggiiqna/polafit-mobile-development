package com.anggiiqna.polafit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.databinding.ActivityHomeBinding
import com.anggiiqna.polafit.databinding.ActivityWelcomeBinding
import com.anggiiqna.polafit.HomeActivity
import com.anggiiqna.polafit.features.login.LoginActivity
import com.anggiiqna.polafit.features.register.SignUpActivity
import com.anggiiqna.polafit.pref.AppPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var welcomeBinding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra("token") ?: ""

        Log.d("TokenExtraction", "Token extracted: $token")

        if (token.isNotEmpty()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(welcomeBinding.root)

        welcomeBinding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        welcomeBinding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}