package com.example.retrofit_room_example.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.retrofit_room_example.model.local.BreedEntity
import com.example.retrofit_room_example.model.local.DogDao
import com.example.retrofit_room_example.model.local.ImagesEntity
import com.example.retrofit_room_example.model.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogRepository(private val mDogDao: DogDao) {

    private val mRetrofit = RetrofitClient.retrofitInstance()
    // liveData
    val allBreedList = mDogDao.getAllBreedList()

    fun getImagesByBreed(breed: String): LiveData<List<ImagesEntity>> {
        return mDogDao.getAllImagesByBreed(breed)
    }


    fun getBreedFromApiWithCoroutines() = CoroutineScope(Dispatchers.IO).launch {
        val service = kotlin.runCatching { mRetrofit.fetchAllBreeds() }
        service.onSuccess {
            when(it.code()) {
                in 200..299 -> it.body()?.let {
                    val data = convertBreedList(it.message)
                    mDogDao.insertAllBreed(data)
                }
                in 300..599 -> Log.e("ERROR", it.errorBody().toString())
                else -> Log.d("ERROR", it.errorBody().toString())
            }
        }
        service.onFailure {
            Log.e("ERROR", it.message.toString())
        }
    }

    fun getImagesFromApiWithCoroutines(breed: String) = CoroutineScope(Dispatchers.IO).launch {
        val service = kotlin.runCatching { mRetrofit.fetchImageByBreed(breed) }
        service.onSuccess {
            when(it.code()) {
                in 200..299 -> it.body()?.let {
                    val data = convertImages(it.message, breed)
                    mDogDao.insertAllImagesByBreed(data)
                }
                in 300..599 -> Log.d("ERROR", it.errorBody().toString())
                else -> Log.e("ERROR", it.errorBody().toString())
            }
        }
        service.onFailure {
            Log.e("ERROR", it.message.toString())
        }
    }


    fun convertBreedList(list: List<String>) : List<BreedEntity> {
        val listMutable = mutableListOf<BreedEntity>()
        list.map {
            listMutable.add(BreedEntity(it))
        }
        return listMutable
    }


    fun convertImages(list: List<String>, breed: String) : List<ImagesEntity> {
        val listMutable = mutableListOf<ImagesEntity>()
        list.map {
            listMutable.add(ImagesEntity(it, breed))
        }
        return listMutable
    }


}