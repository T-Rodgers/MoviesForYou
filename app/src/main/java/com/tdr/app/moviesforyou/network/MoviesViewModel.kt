package com.tdr.app.moviesforyou.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tdr.app.moviesforyou.BuildConfig
import kotlinx.coroutines.launch
import timber.log.Timber

const val API_KEY: String = BuildConfig.API_KEY
class MoviesViewModel: ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies : LiveData<List<Movie>>
    get() = _movies


    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            _movies.value = MoviesApi.retrofitService.getMovies(API_KEY)
            Timber.i("${_movies.value!!.size}")
        }
    }
}