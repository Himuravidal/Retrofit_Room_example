package com.example.retrofit_room_example.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImagesEntity(@PrimaryKey val imageUrl: String , val breedName: String)