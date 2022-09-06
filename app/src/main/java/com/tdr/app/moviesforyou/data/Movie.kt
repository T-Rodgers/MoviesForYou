package com.tdr.app.moviesforyou.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    val popularity: Double,
    @Json(name = "vote_average")val voteAverage: Double
) : Parcelable

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Int
)

data class TrailersResponse(val results: List<Trailer>)

data class Trailer(val key: String)