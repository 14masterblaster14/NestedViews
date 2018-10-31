package com.example.apirecyclerviewtimber

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 */
interface ApiInterface {

    @GET("movie/popular")
    fun loadMovies(): Call<MoviesResponse>

    /*@GET("movie/top_rated")
    fun getTopRatedMovies (@Query("api_key")  apiKey :String ) : Call<MoviesResponse>*/

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Call<MoviesResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Int, @Query("api_key") apiKey: String): Call<MoviesResponse>
}