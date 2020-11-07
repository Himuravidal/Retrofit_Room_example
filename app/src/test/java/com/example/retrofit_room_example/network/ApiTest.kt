package com.example.retrofit_room_example.network

import com.example.retrofit_room_example.model.network.DoggyApi
import com.example.retrofit_room_example.model.network.RetrofitClient
import com.example.retrofit_room_example.model.network.pojo.Breed
import com.example.retrofit_room_example.model.network.pojo.DogImages
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {

    lateinit var mMockWebServer: MockWebServer
    lateinit var mDoggyApiTest: DoggyApi

    @Before
    fun setUp() {
        mMockWebServer = MockWebServer()
        val mRetrofit = Retrofit.Builder()
            .baseUrl(mMockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
         mDoggyApiTest = mRetrofit.create(DoggyApi::class.java)
    }

    @After
    fun shutDown() {
        mMockWebServer.shutdown()
    }

    @Test
    fun getAllBreedList() = runBlocking {
        // given
        val breedTest = Breed(listOf("affenpinscher", "african","airedale","akita","appenzeller",
            "australian","basenji","beagle","bluetick","borzoi","bouvier","boxer","brabancon",
            "briard","buhund","bulldog"), "success")
        mMockWebServer.enqueue(MockResponse().setBody(Gson().toJson(breedTest)))
        // when
        val result = mDoggyApiTest.fetchAllBreeds()
        //then
        assertThat(result).isNotNull()
        val body = result.body()
        if (body != null) {
            assertThat(body.message).hasSize(16)
            assertThat(body.status).isEqualTo("success")
        }


        val request = mMockWebServer.takeRequest()
        println(request.path)
        assertThat(request.path).isEqualTo("/breeds/list")
    }

    @Test
    fun getImagesByBreed()= runBlocking {
        //given
        val breadImages = DogImages(listOf("https://images.dog.ceo/breeds/affenpinscher/n02110627_10147.jpg"
            ,"https://images.dog.ceo/breeds/affenpinscher/n02110627_10185.jpg",
            "https://images.dog.ceo/breeds/affenpinscher/n02110627_10225.jpg"), "success")
        val breedName = "affenpinscher"
        mMockWebServer.enqueue(MockResponse().setBody(Gson().toJson(breadImages)))
            //when
        val result = mDoggyApiTest.fetchImageByBreed(breedName)
        //then
        val body = result.body()
        assertThat(body?.message).hasSize(3)
    }
}