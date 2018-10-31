package com.example.intermediaterecyclerview.service

import com.example.intermediaterecyclerview.models.PhotoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 */
interface NasaApi {

    //@GET("mars-photos/api/v1/rovers/{rover}/photos?sol=1000&api_key=<key>")
    @GET("mars-photos/api/v1/rovers/{rover}/photos?sol=1000&api_key=gJ4SOAQMkhIBpvOCi9Ne766T4QlqZ2MEFUm9ovou")
    fun getPhotos(@Path("rover") rover: String): Call<PhotoList>
}