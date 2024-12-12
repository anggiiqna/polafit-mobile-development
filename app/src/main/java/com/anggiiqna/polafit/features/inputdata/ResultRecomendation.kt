package com.anggiiqna.polafit.features.inputdata

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.R

class ResultRecomendation : AppCompatActivity() {

    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_exercise)

        result = findViewById(R.id.tv_exercise_rexomendation)

        val recomendation = intent.getStringExtra("result") ?: ""

        result.text = recomendation

        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            finish()
        }
    }
}