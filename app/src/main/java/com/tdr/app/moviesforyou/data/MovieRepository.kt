package com.tdr.app.moviesforyou.data

import com.tdr.app.moviesforyou.network.API_KEY
import com.tdr.app.moviesforyou.network.MoviesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(private val movieDao: MovieDao) {

    val moviesFlow: Flow<List<Movie>> = movieDao.getPopularMoviesFlow()
    val sortedByRatingFlow: Flow<List<Movie>> = movieDao.sortByRatingFlow()

    suspend fun refreshMovies() {
        val response = MoviesApi.retrofitService.getPopularMovies(API_KEY, 1)
        movieDao.insertAll(response.results)
    }
}