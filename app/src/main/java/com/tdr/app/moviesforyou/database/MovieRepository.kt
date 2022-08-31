package com.tdr.app.moviesforyou.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tdr.app.moviesforyou.network.API_KEY
import com.tdr.app.moviesforyou.network.Movie
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.network.asDatabaseModel

class MovieRepository(private val database: MovieDatabase) {

    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getAllPopularMovies()) {
            it.asDomainModel()
        }

    val topRatedMovies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.sortByRating()) {
            it.asDomainModel()
        }


    /**
     * Initial request to
     */
    suspend fun refreshMovies() {
        val movieList = MoviesApi.retrofitService.getPopularMovies(API_KEY, 1)
        database.movieDao.insertAll(*movieList.asDatabaseModel())
    }
}