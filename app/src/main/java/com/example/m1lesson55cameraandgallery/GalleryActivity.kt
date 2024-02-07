package com.example.m1lesson55cameraandgallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.m1lesson55cameraandgallery.databinding.ActivityGalleryBinding
import java.io.File
import java.io.FileOutputStream

class GalleryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGalleryBinding.inflate(layoutInflater) }
    private val OLD_METHOD_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {

            galleryNewBtn.setOnClickListener {

                takePhotoFromGalleryNewMethod()

            }
            galleryOldBtn.setOnClickListener {

                takePhotoFromGalleryOldMethod()

            }

            deleteBtn.setOnClickListener {

                val filesDir = filesDir
                if (filesDir.isDirectory){
                    val listFiles = filesDir.listFiles()
                    listFiles?.forEach {
                        it.delete()
                    }
                }

            }

        }

    }


    private fun takePhotoFromGalleryOldMethod() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, OLD_METHOD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OLD_METHOD_REQUEST_CODE && resultCode == RESULT_OK) {

            val uri = data?.data ?: return
            binding.img.setImageURI(uri)

            val openInputStream = contentResolver?.openInputStream(uri)
            val file = File(filesDir, "${System.currentTimeMillis()}.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()

            Log.d("File path : ", "${file.absolutePath}")

        }
    }

    private val takePhotoResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri == null) return@registerForActivityResult
            binding.img.setImageURI(uri)

            val openInputStream = contentResolver?.openInputStream(uri)
            val file = File(filesDir, "${System.currentTimeMillis()}.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()

            Log.d("File path : ", "${file.absolutePath}")
        }

    private fun takePhotoFromGalleryNewMethod() {
        takePhotoResult.launch("image/*")
    }
}