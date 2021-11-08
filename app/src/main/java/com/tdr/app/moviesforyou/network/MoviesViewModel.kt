package com.tdr.app.moviesforyou.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdr.app.moviesforyou.BuildConfig
import kotlinx.coroutines.launch
import timber.log.Timber

const val API_KEY: String = BuildConfig.API_KEY
enum class MoviesApiStatus { LOADING, ERROR, DONE }
class MoviesViewModel: ViewModel() {

    private val _movies = MutableLiveData<MoviesResponse>()
    val movies : LiveData<MoviesResponse>
    get() = _movies

    private val _status = MutableLiveData<MoviesApiStatus>()
    val status : LiveData<MoviesApiStatus>
    get() = _status

    private val _navigateToDetails = MutableLiveData<Movie>()
    val navigateToDetails : LiveData<Movie>
    get() = _navigateToDetails

    init {
        getPopularMovies()
    }

    fun displayMovieItemDetails(movie: Movie) {
        _navigateToDetails.value = movie
    }

    fun doneNavigating() {
        _navigateToDetails.value = null
    }

    fun getPopularMovies() {
        viewModelScope.launch {
           _status.value = MoviesApiStatus.LOADING
            try {
                _movies.value = MoviesApi.retrofitService.getPopularMovies(API_KEY, 1)
                _status.value = MoviesApiStatus.DONE
                Timber.i(_movies.value!!.totalPages.toString())
            } catch (e :Exception){
               _status.value = MoviesApiStatus.ERROR
                _movies.value = null
                Timber.i("Error retrieving movie list")
            }

        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _status.value = MoviesApiStatus.LOADING
            try {
                _movies.value = MoviesApi.retrofitService.getTopRatedMovies(API_KEY, 1)
                _status.value = MoviesApiStatus.DONE
                Timber.i(_movies.value!!.totalPages.toString())
            } catch (e :Exception){
                _status.value = MoviesApiStatus.ERROR
                _movies.value = null
                Timber.i("Error retrieving movie list")
            }

        }
    }
}