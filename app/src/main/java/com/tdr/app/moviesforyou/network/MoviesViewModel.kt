package com.tdr.app.moviesforyou.network

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.moviesforyou.BuildConfig
import com.tdr.app.moviesforyou.database.MovieRepository
import com.tdr.app.moviesforyou.database.getDatabase
import kotlinx.coroutines.launch

const val API_KEY: String = BuildConfig.API_KEY

enum class MoviesApiStatus { LOADING, ERROR, DONE }
class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<MoviesApiStatus>()
    val status: LiveData<MoviesApiStatus>
        get() = _status

    private val _navigateToDetails = MutableLiveData<Movie>()
    val navigateToDetails: LiveData<Movie>
        get() = _navigateToDetails

    private val _movies = MutableLiveData<LiveData<List<Movie>>>()
    val movies: LiveData<LiveData<List<Movie>>>
        get() = _movies

    private val database = getDatabase(application)
    private val repository = MovieRepository(database)

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
        viewModelScope.launch {
            _status.value = MoviesApiStatus.LOADING

            try {
                _movies.value = repository.movies
                _status.value = MoviesApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = MoviesApiStatus.ERROR
            }
        }
    }

    fun retrieveTopRatedMovies() {
        viewModelScope.launch {
            _status.value = MoviesApiStatus.LOADING

            try {
                _movies.value = repository.topRatedMovies
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