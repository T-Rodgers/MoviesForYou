package com.tdr.app.moviesforyou.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tdr.app.moviesforyou.database.DatabaseMovie
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val results: List<NetworkMovie>,
    @Json(name = "total_pages") val totalPages: Int
)

data class TrailersResponse(val results: List<Trailer>)

data class Trailer(val key: String)

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    val popularity: Double,
    @Json(name = "vote_average")val voteAverage: Double
) : Parcelable

data class NetworkMovie constructor(
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    val popularity: Double,
    @Json(name = "vote_average")val voteAverage: Double
)

fun MoviesResponse.asDatabaseModel(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            backdropPath = it.backdropPath,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            popularity = it.popularity,
            voteAverage = it.voteAverage
        )
    }.toTypedArray()
}