package com.example.viewpager

import android.content.Context
import org.json.JSONObject

/**
 */

object MovieHelper {

    val KEY_TITLE = "title"
    val KEY_RATING = "rating"
    val KEY_POSTER_URI = "posterUri"
    val KEY_OVERVIEW = "overview"

    fun getMoviesFromJson(context: Context, fileName: String): ArrayList<Movie> {

        val movieList = ArrayList<Movie>()

        try {
            // Load the JSONArray from the file
            val jsonString = loadJsonFromFile(context, fileName)
            val json = JSONObject(jsonString)
            val jsonMovies = json.getJSONArray("movies")

            // Create the list of movies

            for (index in 0 until jsonMovies.length()) {
                val movieTitle = jsonMovies.getJSONObject(index).getString(KEY_TITLE)
                val movieRating = jsonMovies.getJSONObject(index).getInt(KEY_RATING)
                val moviePosterURI = jsonMovies.getJSONObject(index).getString(KEY_POSTER_URI)
                val movieOverview = jsonMovies.getJSONObject(index).getString(KEY_OVERVIEW)

                movieList.add(Movie(movieTitle, movieRating, moviePosterURI, movieOverview))
            }

        } catch (e: Exception) {
            return movieList
        }
        return movieList
    }


    private fun loadJsonFromFile(context: Context, filename: String): String {

        var json = ""

        try {
            val input = context.assets.open(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return json
    }

}