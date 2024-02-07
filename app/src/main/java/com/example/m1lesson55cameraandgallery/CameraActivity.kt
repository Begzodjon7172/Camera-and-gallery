package com.example.m1lesson55cameraandgallery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.m1lesson55cameraandgallery.database.AppDatabase
import com.example.m1lesson55cameraandgallery.database.entity.Contact
import com.example.m1lesson55cameraandgallery.databinding.ActivityCameraBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class CameraActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater) }

    lateinit var currentPhotoPath: String
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)

        val list = appDatabase.contactDao().getAllStudents()
        val contact = list[0]

        // byteArraydan bitmap ga o'tlazish
        val bitmap = BitmapFactory.decodeByteArray(
            contact.contactImageFile,
            0,
            contact.contactImageFile.size
        )
        binding.img.setImageBitmap(bitmap)

        binding.apply {

            cameraNewBtn.setOnClickListener {
                takePhotoFromCameraNewMethod()
            }

            cameraOldBtn.setOnClickListener {
                takePhotoFromCameraOldMethod()
            }

            deleteBtn.setOnClickListener {

            }

        }

    }


    private fun takePhotoFromCameraNewMethod() {
        val file = try {
            createImageFile()
        } catch (e: Exception) {
            null
        }
        val uriForFile = file?.let {
            FileProvider.getUriForFile(this, this.packageName, it)
        }

        takePhotoFromCameraResultLauncher.launch(uriForFile)
    }

    //new method

    private val takePhotoFromCameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it && ::currentPhotoPath.isInitialized) {
                binding.img.setImageURI(Uri.fromFile(File(currentPhotoPath)))
            }
        }

    // old method

    private fun takePhotoFromCameraOldMethod() {
        val file = try {
            createImageFile()
        } catch (e: Exception) {
            null
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        val uriForFile = file?.let {
            FileProvider.getUriForFile(this, this.packageName, it)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (::currentPhotoPath.isInitialized) {
            binding.img.setImageURI(Uri.fromFile(File(currentPhotoPath)))

//            val bitmap = data?.extras?.get("data") as Bitmap
//            binding.img.setImageBitmap(bitmap)
//            val byteArrayOutputStream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)


            val contact = Contact(
                name = "Begzodjon",
                phone = "903657172",
                contactPhotoPath = currentPhotoPath,
                contactImageFile = File(currentPhotoPath).readBytes()// byteArray ko'rinishida o'qib olish
            )

            appDatabase.contactDao().addContact(contact)

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("${System.currentTimeMillis()}", ".jpg", externalFilesDir)
            .apply {
                currentPhotoPath = absolutePath
            }
    }
}