package com.anggiiqna.polafit.features.inputdata

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.R

class ResultRecomendation : AppCompatActivity() {

    private lateinit var result: TextView

    private val exerciseDescriptions = mapOf(
        "Lari" to "Latihan sederhana dan efektif yang meningkatkan kesehatan kardiovaskular. Baik untuk rekreasi maupun pelatihan, lari membantu membangun daya tahan dan kekuatan.",
        "Bersepeda" to "Latihan berdampak rendah yang sangat baik untuk memperkuat otot kaki dan meningkatkan kesehatan jantung. Ini adalah cara menyenangkan dan ramah lingkungan untuk tetap aktif serta menjelajahi alam terbuka.",
        "Senam" to "Latihan berenergi tinggi yang menggabungkan gerakan ritmis dengan musik. Sangat baik untuk meningkatkan stamina, fleksibilitas, dan kebugaran tubuh secara keseluruhan.",
        "Angkat Beban" to "Latihan kekuatan yang membangun massa otot dan meningkatkan kepadatan tulang. Ideal untuk meningkatkan kekuatan, daya, dan metabolisme tubuh."
    )

    // Map exercise names to drawable resource IDs
    private val exerciseImages = mapOf(
        "Lari" to R.drawable.ex_lari,
        "Bersepeda" to R.drawable.ex_bersepeda,
        "Senam" to R.drawable.ex_senam,
        "Angkat Beban" to R.drawable.ex_lifting
    )

    private val defaultImage = R.drawable.serving_size
    private val defaultDescription = "Lakukan latihan ini secara rutin sesuai dengan kemampuan Anda. Pastikan pemanasan sebelum latihan dan pendinginan setelahnya."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_exercise)

        result = findViewById(R.id.tv_exercise_rexomendation)
        val description: TextView = findViewById(R.id.exercise_detail)
        val imgExercise: ImageView = findViewById(R.id.tv_exercise_rexomendation_image)


        val recommendation = intent.getStringExtra("result") ?: ""
        val exerciseDesc = exerciseDescriptions[recommendation] ?: defaultDescription

        result.text = recommendation
        description.text = exerciseDesc

        val imageResource = exerciseImages[recommendation] ?: defaultImage
        imgExercise.setImageResource(imageResource)


        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            finish()
        }
    }
}