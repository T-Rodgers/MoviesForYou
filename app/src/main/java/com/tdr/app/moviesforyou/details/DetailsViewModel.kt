package com.tdr.app.moviesforyou.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tdr.app.moviesforyou.network.Movie

class DetailsViewModel(movie: Movie, application: Application) : AndroidViewModel(application) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }
}