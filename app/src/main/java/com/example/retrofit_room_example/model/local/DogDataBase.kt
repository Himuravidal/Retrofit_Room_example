package com.example.retrofit_room_example.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BreedEntity::class, ImagesEntity::class], version = 1)
abstract class DogDataBase : RoomDatabase() {
    abstract fun dogDao(): DogDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DogDataBase? = null

        fun getDatabase(context: Context): DogDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDataBase::class.java,
                    "breed_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}