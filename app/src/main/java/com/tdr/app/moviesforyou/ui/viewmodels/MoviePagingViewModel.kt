package com.tdr.app.moviesforyou.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tdr.app.moviesforyou.data.MoviePagingRepository
import com.tdr.app.moviesforyou.model.Movie
import kotlinx.coroutines.flow.Flow

class MoviePagingViewModel(private val moviePagingRepository: MoviePagingRepository) : ViewModel() {

    fun getMovieList(): Flow<PagingData<Movie>> {
        return moviePagingRepository.getAllMovies().cachedIn(viewModelScope)
    }
}

@Suppress("UNCHECKED_CAST")
class MoviePagingViewModelFactory(private val repository: MoviePagingRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviePagingViewModel::class.java)) {
            return MoviePagingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

