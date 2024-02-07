package com.example.m1lesson55cameraandgallery

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.m1lesson55cameraandgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

        binding.apply {

            cameraBtn.setOnClickListener {

                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                startActivity(intent)

            }

            galleryBtn.setOnClickListener {

                val intent = Intent(this@MainActivity, GalleryActivity::class.java)
                startActivity(intent)

            }

        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map[Manifest.permission.CAMERA] == true && map[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {

            } else {

            }
        }

}