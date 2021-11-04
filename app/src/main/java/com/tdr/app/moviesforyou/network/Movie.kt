package com.tdr.app.moviesforyou.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class MoviesResponse(val results: List<Movie>)

data class TrailersResponse(val results: List<Trailer>)

data class Trailer(val id: String)

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String
) : Parcelable