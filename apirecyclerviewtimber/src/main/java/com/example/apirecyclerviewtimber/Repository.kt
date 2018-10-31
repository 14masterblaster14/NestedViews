package com.example.apirecyclerviewtimber

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 */
class Repository {


    fun getMovies() { //: List<Movie> {

        var movieList: List<Movie>

        ApiClient.getApiInterface().loadMovies().enqueue(object : Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                response.let { response1 ->
                    if (response1.isSuccessful) {
                        val body = response1.body()
                        body.let {

                            val movieList1 = body?.results!!
                            Log.d("MasterBlasterRepo", "Movies Size Received ${movieList1.size}")
                            Log.d("MasterBlasterRepo", "Movies Received $movieList1")
                        }

                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable?) {
                Log.e("MasterBlaster", "Error in loading movies")
            }
        })

        //return movieList = movieList1
    }


    fun getMoviesL(): LiveData<List<Movie>> {

        //fun getMovies() : List<Movie> {

        val liveMovieList = MutableLiveData<List<Movie>>()

        ApiClient.getApiInterface().loadMovies().enqueue(object : Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                response.let { response1 ->
                    if (response1.isSuccessful) {
                        val body = response1.body()
                        body.let {

                            val movieList = body?.results
                            Log.d("MasterBlasterRepo", "Movies Size Received ${movieList?.size}")
                            Log.d("MasterBlasterRepo", "Movies Received $movieList")
                            liveMovieList.postValue(movieList)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable?) {
                Log.e("MasterBlaster", "Error in loading movies")
            }
        })

        return liveMovieList
    }


}