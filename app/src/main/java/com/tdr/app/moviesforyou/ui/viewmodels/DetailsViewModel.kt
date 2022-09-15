package com.tdr.app.moviesforyou.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tdr.app.moviesforyou.data.TrailersRepository
import com.tdr.app.moviesforyou.model.Movie
import com.tdr.app.moviesforyou.model.Trailer
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(movie: Movie, application: Application) : AndroidViewModel(application) {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _trailer = MutableLiveData<Trailer>()
    val trailer: LiveData<Trailer>
        get() = _trailer

    private val _trailers = MutableLiveData<List<Trailer?>>()
    val trailers: LiveData<List<Trailer?>>
        get() = _trailers

    private val repository = TrailersRepository()

    init {
        _selectedMovie.value = movie
        getTrailers(movie)
    }

    private fun getTrailers(movie: Movie) {
        viewModelScope.launch {
            try {
                _trailers.value = repository.fetchTrailers(movie)
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
        if (_trailers.value?.isEmpty() == true) {
            Toast.makeText(
                context,
                "No Trailer Found",
                Toast.LENGTH_LONG
            ).show()
        } else {
          val trailerKey = _trailers.value?.get(0)?.key.toString()
            val trailerIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$trailerKey"))
            context.startActivity(trailerIntent)
        }

    }
}