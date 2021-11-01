package com.tdr.app.moviesforyou.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MoviesResponse(val results: List<Movie>)

@Parcelize
data class Movie(val id: Int, val title: String, val overview: String) : Parcelable