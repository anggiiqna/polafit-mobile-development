package com.anggiiqna.polafit.features.scan

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.features.history.HistoryActivity
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.ApiService
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream

class ScanResultActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result)

        val nameFoodResultTextView: TextView = findViewById(R.id.name_food_result)
        val servingSizeResultTextView: TextView = findViewById(R.id.serving_size_result)
        val caloriesTextView: TextView = findViewById(R.id.calories_text_view)
        val proteinTextView: TextView = findViewById(R.id.protein_text_view)
        val fatTextView: TextView = findViewById(R.id.fat_text_view)
        val carboTextView: TextView = findViewById(R.id.carbo_text_view)
        val fiberTextView: TextView = findViewById(R.id.fiber_text_view)
        val sugarTextView: TextView = findViewById(R.id.sugar_text_view)
        val foodImageView: ImageView = findViewById(R.id.food_image)
        val backButton: ImageView = findViewById(R.id.icon_back)
        val saveButton: Button = findViewById(R.id.btnResult)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)

        val id = intent.getStringExtra("id")

        backButton.setOnClickListener {
            val intent = Intent(this@ScanResultActivity, HistoryActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }

        val foodName = intent.getStringExtra("Makanan")
        val servingSizeValue = intent.getStringExtra("Berat_per_Serving") ?: ""
        val caloriesValue = intent.getStringExtra("Kalori") ?: ""
        val proteinValue = intent.getStringExtra("Protein") ?: ""
        val fatValue = intent.getStringExtra("Lemak") ?: ""
        val carboValue = intent.getStringExtra("Karbohidrat") ?: ""
        val fiberValue = intent.getStringExtra("Serat") ?: ""
        val sugarValue = intent.getStringExtra("Gula") ?: ""
        val imageUriString = intent.getStringExtra("ImageUri")

        nameFoodResultTextView.text = foodName
        servingSizeResultTextView.text = "$servingSizeValue Gram"
        caloriesTextView.text = "$caloriesValue Kcal"
        proteinTextView.text = "$proteinValue Gram"
        fatTextView.text = "$fatValue Gram"
        carboTextView.text = "$carboValue Gram"
        fiberTextView.text = "$fiberValue Gram"
        sugarTextView.text = "$sugarValue Gram"

        imageUriString?.let { uriString ->
            val imageUri = Uri.parse(uriString)
            foodImageView.setImageURI(imageUri)
        }

        saveButton.setOnClickListener {
            val userId = id
            val foodNamePart = RequestBody.create(MultipartBody.FORM, foodName ?: "")
            val servingPart = RequestBody.create(MultipartBody.FORM, servingSizeValue ?: "")
            val caloriesPart = RequestBody.create(MultipartBody.FORM, caloriesValue ?: "")
            val proteinPart = RequestBody.create(MultipartBody.FORM, proteinValue ?: "")
            val fatPart = RequestBody.create(MultipartBody.FORM, fatValue ?: "")
            val carboPart = RequestBody.create(MultipartBody.FORM, carboValue ?: "")
            val fiberPart = RequestBody.create(MultipartBody.FORM, fiberValue ?: "")
            val sugarPart = RequestBody.create(MultipartBody.FORM, sugarValue ?: "")

            val imageUri = Uri.parse(imageUriString)
            val imageFile = imageUri?.let {
                getFileFromUri(it)
            }
            val imagePart = imageFile?.let {
                val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", it.name, requestBody)
            }

            sendFoodHistory(
                userId.toString(), foodNamePart, servingPart, caloriesPart,
                proteinPart, fatPart, carboPart, fiberPart, sugarPart, imagePart
            )

            lifecycleScope.launch {
                try {
                    Toast.makeText(this@ScanResultActivity, "History saved..", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("ScanResultActivity", "Error while sending data: ${e.message}")
                }
            }
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val file = File(filesDir, uri.lastPathSegment ?: "temp_image")
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file.takeIf { it.exists() }
    }

    private fun sendFoodHistory(
        userId: String,
        foodName: RequestBody,
        serving: RequestBody,
        calories: RequestBody,
        protein: RequestBody,
        fat: RequestBody,
        carbs: RequestBody,
        fiber: RequestBody,
        sugar: RequestBody,
        image: MultipartBody.Part?
    ) {
        apiService = ApiClient.create()

        lifecycleScope.launch {
            try {
                val response = apiService.storeFoodHistory(
                    RequestBody.create(MultipartBody.FORM, userId),
                    foodName, serving, calories, protein, fat, carbs, fiber, sugar, image ?: MultipartBody.Part.createFormData("image", "")
                )

            } catch (e: Exception) {
                Log.e("FoodHistory", "Exception: ${e.message}")
            }
        }
    }
}
