package com.example.apirecyclerviewtimber


import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 */
object ApiClient {

    private val apiInterface: ApiInterface

    init {

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(ApiConstant.TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
                .readTimeout(ApiConstant.TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)
                .addInterceptor(RequestInterceptor()).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstant.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun getApiInterface(): ApiInterface {
        return apiInterface
    }

    //fun getMovies() : Call<MoviesResponse> = apiInterface.loadMovies()
}