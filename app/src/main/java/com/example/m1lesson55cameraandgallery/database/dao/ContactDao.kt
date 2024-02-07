package com.example.m1lesson55cameraandgallery.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.m1lesson55cameraandgallery.database.entity.Contact

@Dao
interface ContactDao {

    @Insert
    fun addContact(contact: Contact)

    @Query("select * from contact")
    fun getAllStudents(): List<Contact>
}