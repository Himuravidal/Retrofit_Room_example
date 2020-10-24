package com.example.retrofit_room_example.model.network.pojo


import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("message")
    val message: List<String>,
    @SerializedName("status")
    val status: String
)