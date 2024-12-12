package com.anggiiqna.polafit.features.inputdata

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anggiiqna.polafit.R
import com.anggiiqna.polafit.network.ApiService
import com.anggiiqna.polafit.network.ApiClient
import com.anggiiqna.polafit.network.datamodel.ExerciseRequest
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class InputActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_isidata)

        apiService = ApiClient.createML()

        val etTinggi: EditText = findViewById(R.id.et_tinggi)
        val etBerat: EditText = findViewById(R.id.et_berat)
        val etDurasi: EditText = findViewById(R.id.et_durasi)
        val etKalori: EditText = findViewById(R.id.et_kalori)
        val etUmur: EditText = findViewById(R.id.et_umur)

        val spTingkatAktivitas: Spinner = findViewById(R.id.sp_tingkat_aktivitas)
        val spTujuan: Spinner = findViewById(R.id.sp_tujuan)
        val spKategori: Spinner = findViewById(R.id.sp_kategori)
        val spJenisKelamin: Spinner = findViewById(R.id.sp_jenis_kelamin)

        val btnSave: Button = findViewById(R.id.btn_save)

        ArrayAdapter.createFromResource(
            this,
            R.array.tingkat_aktivitas_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTingkatAktivitas.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.tujuan_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spTujuan.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.kategori_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spKategori.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kelamin_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spJenisKelamin.adapter = adapter
        }

        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val request = ExerciseRequest(
                tinggi = etTinggi.text.toString().toIntOrNull() ?: 0,
                berat = etBerat.text.toString().toIntOrNull() ?: 0,
                durasi = etDurasi.text.toString().toIntOrNull() ?: 0,
                kalori_terbakar = etKalori.text.toString().toIntOrNull() ?: 0,
                umur = etUmur.text.toString().toIntOrNull() ?: 0,
                tingkat_aktivitas = spTingkatAktivitas.selectedItem.toString(),
                tujuan = spTujuan.selectedItem.toString(),
                kategori = spKategori.selectedItem.toString(),
                jenis_kelamin = spJenisKelamin.selectedItem.toString()
            )

            sendExerciseRecommendationRequest(request)
        }
    }

    private fun sendExerciseRecommendationRequest(request: ExerciseRequest) {
        // Show loading indicator before API call
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        lifecycleScope.launch {
            try {
                val response = apiService.getExerciseRecommendation(request)
                val recomendation = response.recommendations

                progressDialog.dismiss()
                goToResult(recomendation)
            } catch (ex: Exception) {
                progressDialog.dismiss()
                Toast.makeText(this@InputActivity, "Error: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToResult(result: String) {
        val intent = Intent(this, ResultRecomendation::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
    }
}