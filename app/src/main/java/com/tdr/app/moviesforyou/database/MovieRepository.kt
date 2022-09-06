package com.tdr.app.moviesforyou.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tdr.app.moviesforyou.network.API_KEY
import com.tdr.app.moviesforyou.network.Movie
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.network.asDatabaseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

const val BY_RATING_VALUE = 0
class MovieRepository(private val database: MovieDatabase) {

//    val movies: LiveData<List<Movie>> =
//        Transformations.map(database.movieDao.getAllPopularMovies()) {
//            it.asDomainModel()
//        }

    val topRatedMovies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.sortByRating()) {
            it.asDomainModel()
        }

    val moviesFlow: Flow<List<Movie>> = database.movieDao.getPopularMoviesFlow()
    val sortedByRatingFlow: Flow<List<Movie>> = database.movieDao.sortByRatingFlow()

    suspend fun refreshMovies() {
        val response = MoviesApi.retrofitService.getPopularMovies(API_KEY, 1)
        database.movieDao.insertAll(response.results)
    }
}