package com.tdr.app.moviesforyou.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class MoviesResponse(val results: List<Movie>, @Json(name = "total_pages") val totalPages: Int)

data class TrailersResponse(val results: List<Trailer>)

data class Trailer(val key: String)

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date")val releaseDate: String
) : Parcelable