package com.anggiiqna.polafit.features.scan

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anggiiqna.polafit.R


class ScanActivity : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 100
    private val GALLERY_REQUEST_CODE = 101
    private var selectedBitmap: Bitmap? = null // Variable to store the selected image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        // Request camera and gallery permissions if not already granted
        requestPermissions()

        // Back button functionality
        val backButton: ImageView = findViewById(R.id.icon_back)
        backButton.setOnClickListener {
            finish() // Close the current activity
        }

        // Open gallery button functionality
        val btnOpenFile: ImageButton = findViewById(R.id.btnOpenFile)
        btnOpenFile.setOnClickListener {
            openGallery() // Open the gallery to select an image
        }

        val tvOpenFile: TextView = findViewById(R.id.tv_open_file)
        tvOpenFile.setOnClickListener {
            openGallery()
        }

        // Detect button functionality (to send the image to the API for prediction)
        val btnDetect: Button = findViewById(R.id.btnDetect)
        btnDetect.setOnClickListener {
            selectedBitmap?.let {

            }
        }

        val tvDetect: TextView = findViewById(R.id.tv_detect)
        tvDetect.setOnClickListener {
            selectedBitmap?.let {

            }
        }

        // Take picture button functionality (to open the camera)
        val btnTakePicture: ImageButton = findViewById(R.id.btnTakePicture)
        btnTakePicture.setOnClickListener {
            openCamera()
        }

        val tvTakePicture: TextView = findViewById(R.id.tv_take_picture)
        tvTakePicture.setOnClickListener {
            openCamera()
        }
    }

    // Request necessary permissions (camera and gallery)
    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                GALLERY_REQUEST_CODE
            )
        }
    }

    // Open the gallery to select an image
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    // Open the camera to take a picture
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    // Handle the result of selecting an image from the gallery or taking a picture with the camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val imageView: ImageView = findViewById(R.id.food_image)

            if (requestCode == CAMERA_REQUEST_CODE) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
                selectedBitmap = imageBitmap
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                val imageUri: Uri? = data?.data
                val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageView.setImageBitmap(imageBitmap)
                selectedBitmap = imageBitmap
            }
        }
    }
}
