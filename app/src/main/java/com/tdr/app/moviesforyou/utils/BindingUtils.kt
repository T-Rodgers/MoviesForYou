package com.tdr.app.moviesforyou.utils

import android.os.Build
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.adapters.MovieListAdapter
import com.tdr.app.moviesforyou.network.Movie
import com.tdr.app.moviesforyou.network.MoviesApiStatus


private const val BASE_BACKDROP_PATH_URL = "https://image.tmdb.org/t/p/w780" // 780x439
private const val BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w300" // 300x450

/**
 * Binds recyclerview to List of movies returned from response.
 */
@BindingAdapter("movieList")
fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieListAdapter
    movies?.let {
        recyclerView.scheduleLayoutAnimation()
        adapter.submitList(movies)
    }

}

@BindingAdapter("statusImage")
fun bindStatus(statusImageView: ImageView, status: MoviesApiStatus?) {
    when (status) {
        MoviesApiStatus.LOADING -> {
            statusImageView.visibility = GONE
        }
        MoviesApiStatus.ERROR -> {
            statusImageView.visibility = VISIBLE
            statusImageView.setImageResource(R.drawable.ic_twotone_error_24)
        }
        MoviesApiStatus.DONE -> {
            statusImageView.visibility = GONE
        }
    }
}

@BindingAdapter("statusIndicator")
fun bindProgress(progress: ProgressBar, status: MoviesApiStatus?) {

    when (status) {

        MoviesApiStatus.LOADING -> {
            progress.visibility = VISIBLE
        }
        MoviesApiStatus.ERROR -> {
            progress.visibility = GONE
        }
        MoviesApiStatus.DONE -> {
            progress.visibility = GONE

        }
    }
}

@BindingAdapter("posterImage")
fun bindPostImage(posterImageView: ImageView, movie: Movie?) {
    movie?.let {
        Glide.with(posterImageView.context)
            .load(BASE_POSTER_PATH_URL + movie.posterPath)
            .into(posterImageView)
    }
}

@BindingAdapter("backdropImage")
fun bindBackdropPhoto(backdropImageView: ImageView, movie: Movie?) {
    movie?.let {
        Glide.with(backdropImageView.context)
            .load(BASE_BACKDROP_PATH_URL + movie.backdropPath)
            .centerCrop()
            .into(backdropImageView)
    }
}

@BindingAdapter("overview")
fun TextView.setOverviewText(movie: Movie?) {
    text = movie?.let { movie.overview }
}

@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("releaseDate")
fun TextView.setReleaseDate(movie: Movie?) {
    text = movie?.let{
        context.getString(R.string.release_date, formatReleaseDate(movie.releaseDate))
    }
}

