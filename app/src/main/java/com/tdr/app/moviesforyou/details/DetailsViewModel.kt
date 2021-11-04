package com.tdr.app.moviesforyou.details

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.moviesforyou.network.API_KEY
import com.tdr.app.moviesforyou.network.Movie
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.network.Trailer
import kotlinx.coroutines.launch

class DetailsViewModel(movie: Movie, application: Application) : AndroidViewModel(application) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _trailer = MutableLiveData<Trailer>()
    val trailer: LiveData<Trailer>
    get() = _trailer

    init {
        _selectedMovie.value = movie
        getTrailer()
    }

    fun getTrailer() {
        viewModelScope.launch {
            val key = _selectedMovie.value?.id
            _trailer.value = MoviesApi.retrofitService.getTrailers(key!!, API_KEY).results[0]
        }
    }

    fun initializeTrailer(context: Context) {
        val trailerKey = _trailer.value!!.key
        val trailerIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$trailerKey"))
        context.startActivity(trailerIntent)
    }
}