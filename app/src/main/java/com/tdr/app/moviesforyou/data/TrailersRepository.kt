package com.tdr.app.moviesforyou.data

import com.tdr.app.moviesforyou.model.Movie
import com.tdr.app.moviesforyou.model.Trailer
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.ui.viewmodels.API_KEY

class TrailersRepository {

    suspend fun fetchTrailers(movie: Movie): List<Trailer> {

        val response = MoviesApi.retrofitService.getTrailers(movie.id,  API_KEY)
        return response.results
    }

}