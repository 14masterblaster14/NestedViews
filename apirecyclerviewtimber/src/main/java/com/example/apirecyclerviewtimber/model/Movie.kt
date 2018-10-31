package com.example.apirecyclerviewtimber

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 */
class Movie {

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("adult")
    @Expose
    var adult: Boolean = false

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
}