package com.tdr.app.moviesforyou.network

import android.app.Application
import androidx.lifecycle.*
import com.tdr.app.moviesforyou.BuildConfig
import com.tdr.app.moviesforyou.database.BY_RATING_VALUE
import com.tdr.app.moviesforyou.database.MovieRepository
import com.tdr.app.moviesforyou.database.getDatabase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Double

const val API_KEY: String = BuildConfig.API_KEY

enum class MoviesApiStatus { LOADING, ERROR, DONE }

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<MoviesApiStatus>()
    val status: LiveData<MoviesApiStatus>
        get() = _status

    private val _navigateToDetails = MutableLiveData<Movie?>()
    val navigateToDetails: LiveData<Movie?>
        get() = _navigateToDetails


    val _movies = MutableLiveData<LiveData<List<Movie>>>()
    val movies : LiveData<LiveData<List<Movie>>>
    get() = _movies

    private val database = getDatabase(application)
    private val repository = MovieRepository(database)

    val moviesFlow = repository.moviesFlow.asLiveData()
    val sortedByRatingFlow = repository.sortedByRatingFlow.asLiveData()

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

            _status.value = MoviesApiStatus.LOADING

            try {
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