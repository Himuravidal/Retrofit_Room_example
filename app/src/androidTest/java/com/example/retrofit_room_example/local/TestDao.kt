package com.example.retrofit_room_example.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.retrofit_room_example.model.local.BreedEntity
import com.example.retrofit_room_example.model.local.DogDao
import com.example.retrofit_room_example.model.local.DogDataBase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestDao {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mDogDao: DogDao
    lateinit var db: DogDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DogDataBase::class.java).build()
        mDogDao = db.dogDao()
    }
    @After
    fun shutDown() {
        db.close()
    }

    @Test
    fun insertBreedList() = runBlocking {
        //given
        val breedList = listOf<BreedEntity>(BreedEntity("affenpinscher"),BreedEntity("african"),
            BreedEntity("briard"), BreedEntity("buhund"), BreedEntity("bulldog"))
        //when
            mDogDao.insertAllBreed(breedList)
        //then
            mDogDao.getAllBreedList().observeForever {
                assertThat(it).isNotEmpty()
              //  assertThat(it).hasSize(5)
              //  assertThat(it[0].breedName).isEqualTo("affenpinscher")
            }

    }


    // TODO Hacer un test que compruebe que se insertaron las imagenes de perro de una raza
    // Determinada y devuelva un LiveData
    @Test
    fun obtainBreedList() = runBlocking{


    }




}


