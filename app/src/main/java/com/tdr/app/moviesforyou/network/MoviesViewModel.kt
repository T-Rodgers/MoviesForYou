package com.tdr.app.moviesforyou.network

import android.app.Application
import androidx.lifecycle.*
import com.tdr.app.moviesforyou.BuildConfig
import com.tdr.app.moviesforyou.data.LocalDB
import com.tdr.app.moviesforyou.data.Movie
import com.tdr.app.moviesforyou.data.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val API_KEY: String = BuildConfig.API_KEY

enum class MoviesApiStatus { LOADING, ERROR, DONE }

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<MoviesApiStatus>()
    val status: LiveData<MoviesApiStatus>
        get() = _status

    private val _navigateToDetails = MutableLiveData<Movie?>()
    val navigateToDetails: LiveData<Movie?>
        get() = _navigateToDetails


    private val _movies = MutableLiveData<LiveData<List<Movie>>>()
    val movies: LiveData<LiveData<List<Movie>>>
        get() = _movies

    private val movieDao = LocalDB.createMovieDao(application.applicationContext)
    private val repository = MovieRepository(movieDao)

    private val moviesFlow = repository.moviesFlow.asLiveData()

    private val sortedByRatingFlow = repository.sortedByRatingFlow.asLiveData()

    init {
        retrievePopularMovies()
        viewModelScope.launch {
            try {
                repository.refreshMovies()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun retrievePopularMovies() {
        loadMovieData {
            _movies.value = moviesFlow

        }
    }

    fun sortByTopRated() {
        loadMovieData {
            _movies.value = sortedByRatingFlow
        }
    }

    private fun loadMovieData(block: suspend () -> Unit): Job {
        return viewModelScope.launch {

            _status.postValue(MoviesApiStatus.LOADING)

            try {
                delay(1_000)
                block()
                _status.value = MoviesApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = MoviesApiStatus.ERROR
            }
        }
    }

    fun displayMovieItemDetails(movie: Movie) {
        _navigateToDetails.value = movie

    }

    fun doneNavigating() {
        _navigateToDetails.value = null
    }
}