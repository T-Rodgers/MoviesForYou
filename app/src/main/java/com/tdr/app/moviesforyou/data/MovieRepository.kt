package com.tdr.app.moviesforyou.data

import com.tdr.app.moviesforyou.db.MovieDao
import com.tdr.app.moviesforyou.model.Movie
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.ui.viewmodels.API_KEY
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    val moviesFlow: Flow<List<Movie>> = movieDao.getPopularMoviesFlow()
    val sortedByRatingFlow: Flow<List<Movie>> = movieDao.sortByRatingFlow()

    suspend fun refreshMovies() {
        val response = MoviesApi.retrofitService.getPopularMovies(API_KEY, 1)
        movieDao.insertAll(response.results)
    }
}