package com.example.retrofit_room_example.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.retrofit_room_example.model.DogRepository
import com.example.retrofit_room_example.model.local.BreedEntity
import com.example.retrofit_room_example.model.local.DogDataBase
import com.example.retrofit_room_example.model.local.ImagesEntity

class DogViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DogRepository
    val breedLivedata : LiveData<List<BreedEntity>>
    init {
        val mDao = DogDataBase.getDatabase(application).dogDao()
        mRepository = DogRepository(mDao)
        breedLivedata = mRepository.allBreedList
        mRepository.getBreedFromApiWithCoroutines()
    }

    fun getImagesByBreed(breed: String): LiveData<List<ImagesEntity>> {
        mRepository.getImagesFromApiWithCoroutines(breed)
        return mRepository.getImagesByBreed(breed)
    }

}