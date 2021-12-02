package com.tdr.app.moviesforyou.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tdr.app.moviesforyou.network.API_KEY
import com.tdr.app.moviesforyou.network.Movie
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MovieDatabase) {

    val movies : LiveData<List<Movie>> = Transformations.map(database.movieDao.getAllPopularMovies()) {
        it.asDomainModel()
    }

    val topRatedMovies : LiveData<List<Movie>> = Transformations.map(database.movieDao.getAllTopRatedMovies()){
        it.asDomainModel()
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val movieList = MoviesApi.retrofitService.getPopularMoviesAsync(API_KEY, 1).await()
            database.movieDao.insertAll(*movieList.asDatabaseModel())
        }
    }
}