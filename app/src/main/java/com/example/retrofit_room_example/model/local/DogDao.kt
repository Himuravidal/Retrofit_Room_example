package com.example.retrofit_room_example.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBreed(list: List<BreedEntity>)

    @Query("SELECT * from breed_table")
    fun getAllBreedList(): LiveData<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImagesByBreed(list: List<ImagesEntity>)

    @Query("SELECT * FROM images_table WHERE breedName =:mBreedName")
    fun getAllImagesByBreed(mBreedName: String): LiveData<List<ImagesEntity>>

}