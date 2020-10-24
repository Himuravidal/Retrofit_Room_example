package com.example.retrofit_room_example.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed_table")
data class BreedEntity(@PrimaryKey val breedName : String)
