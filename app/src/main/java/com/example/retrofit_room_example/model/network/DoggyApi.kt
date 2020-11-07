package com.example.retrofit_room_example.model.network

import com.example.retrofit_room_example.model.network.pojo.Breed
import com.example.retrofit_room_example.model.network.pojo.DogImages
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface DoggyApi {

    @GET("breeds/list")
    suspend fun fetchAllBreeds(): Response<Breed>

    @GET("breed/{breed}/images")
    suspend fun fetchImageByBreed(@Path("breed") breed: String) : Response<DogImages>

}