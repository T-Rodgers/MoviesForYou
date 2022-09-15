package com.tdr.app.moviesforyou.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tdr.app.moviesforyou.model.Movie

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Int
)
