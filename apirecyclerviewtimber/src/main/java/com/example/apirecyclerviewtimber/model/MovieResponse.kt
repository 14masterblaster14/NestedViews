package com.example.apirecyclerviewtimber

import java.io.Serializable
import com.google.gson.annotations.SerializedName


/**
 */

class MoviesResponse {

    @SerializedName("page")
    var page: Int? = null

    @SerializedName("results")
    var results: List<Movie>? = null

    @SerializedName("total_results")
    var totalResults: Int? = null

    @SerializedName("total_pages")
    var totalPages: Int? = null
}
