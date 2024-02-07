package com.example.m1lesson55cameraandgallery.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var phone: String,
    @ColumnInfo(name = "image_path")
    var contactPhotoPath: String,
    @ColumnInfo(name = "image_file")
    var contactImageFile: ByteArray
)
