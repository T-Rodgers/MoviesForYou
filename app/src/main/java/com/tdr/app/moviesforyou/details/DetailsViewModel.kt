package com.tdr.app.moviesforyou.details

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.moviesforyou.network.*
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(movie: Movie, application: Application) : AndroidViewModel(application) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _trailer = MutableLiveData<Trailer>()
    val trailer: LiveData<Trailer>
        get() = _trailer

    private val _trailers = MutableLiveData<TrailersResponse>()
    val trailers: LiveData<TrailersResponse>
        get() = _trailers

    init {
        _selectedMovie.value = movie
        getTrailers(movie)
    }

    private fun getTrailers(movie: Movie) {
        val id = movie.id
        viewModelScope.launch {
            try {
                _trailers.value = MoviesApi.retrofitService.getTrailers(id, API_KEY)
            } catch (e: Exception){
                Timber.i("Unable to load trailers")
            }
        }
    }

    /**
     * This method launches an intent for youtube for the first trailer in our TrailerResponse.
     * If the selected Movie does not contain any trailers. A toast will be shown
     * acknowledging "No trailer found".
     */
    fun initializeTrailer(context: Context) {
        var trailerKey = ""
        if (_trailers.value?.results?.isEmpty() == true) {
            Toast.makeText(
                context,
                "No trailer found",
                Toast.LENGTH_LONG
            ).show()
        } else {
           trailerKey = _trailers.value?.results?.get(0)?.key.toString()
            val trailerIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$trailerKey"))
            context.startActivity(trailerIntent)
        }

    }
}