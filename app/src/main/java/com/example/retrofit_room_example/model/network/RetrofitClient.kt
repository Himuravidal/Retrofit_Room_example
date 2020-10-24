package com.example.retrofit_room_example.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL= "https://dog.ceo/api/"

        fun retrofitInstance(): DoggyApi {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitClient.create(DoggyApi::class.java)
        }
    }
}