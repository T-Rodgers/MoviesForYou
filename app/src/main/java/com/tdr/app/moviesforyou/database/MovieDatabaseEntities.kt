package com.tdr.app.moviesforyou.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.tdr.app.moviesforyou.network.Movie

@Entity(tableName = "movies")
data class DatabaseMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "release_date") val releaseDate: String,
    val popularity: Double,
    @Json(name = "vote_average")val voteAverage: Double
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            backdropPath = it.backdropPath,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            popularity = it.popularity,
            voteAverage = it.voteAverage
        )
    }
}