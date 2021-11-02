package com.tdr.app.moviesforyou.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.adapters.MovieListAdapter
import com.tdr.app.moviesforyou.network.Movie
import com.tdr.app.moviesforyou.network.MoviesApiStatus

@BindingAdapter("movieId")
fun TextView.setMovieId(movie: Movie?) {
    text = movie?.let {
        movie.id.toString()
    }
}

/**
 * Binds TextView to set movie Title
 */
@BindingAdapter("movieTitle")
fun TextView.setMovieTitle(movie: Movie?) {
    text = movie?.let {
        movie.title
    }
}

/**
 * Binds recyclerview to List of movies returned from response.
 */
@BindingAdapter("movieList")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieListAdapter
    movies?.let { adapter.submitList(movies) }

}

@BindingAdapter("statusImage")
fun bindStatus(statusImageView: ImageView, status: MoviesApiStatus?) {
    when (status) {
        MoviesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MoviesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_twotone_error_24)
        }
        MoviesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("statusText")
fun bindStatusText(statusTextView: TextView, status: MoviesApiStatus?) {
    when (status) {

        MoviesApiStatus.LOADING -> {
            statusTextView.visibility = View.GONE
        }
        MoviesApiStatus.ERROR -> {

            statusTextView.visibility = View.VISIBLE

        }
        MoviesApiStatus.DONE -> {
            statusTextView.visibility = View.GONE

        }
    }
}

