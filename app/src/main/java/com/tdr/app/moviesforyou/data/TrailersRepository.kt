package com.tdr.app.moviesforyou.data

import com.tdr.app.moviesforyou.network.API_KEY
import com.tdr.app.moviesforyou.network.MoviesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class TrailersRepository {

    suspend fun fetchTrailers(movie: Movie): List<Trailer> {

        val response = MoviesApi.retrofitService.getTrailers(movie.id,  API_KEY)
        return response.results
    }

}