package com.tdr.app.moviesforyou.utils

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.model.Movie


private const val BASE_BACKDROP_PATH_URL = "https://image.tmdb.org/t/p/w780" // 780x439
private const val BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w300" // 300x450

@BindingAdapter("posterImage")
fun bindPostImage(posterImageView: ImageView, movie: Movie?) {
    movie?.let {
        Glide.with(posterImageView.context)
            .load(BASE_POSTER_PATH_URL + movie.posterPath)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(posterImageView)
    }
}

@BindingAdapter("backdropImage")
fun bindBackdropPhoto(backdropImageView: ImageView, movie: Movie?) {
    movie?.let {
        Glide.with(backdropImageView.context)
            .load(BASE_BACKDROP_PATH_URL + movie.backdropPath)
            .centerCrop()
            .error(R.drawable.ic_launcher_foreground)
            .into(backdropImageView)
    }
}

@BindingAdapter("overview")
fun TextView.setOverviewText(movie: Movie?) {
    text = if (movie?.overview.isNullOrBlank()) "No Overview Found" else movie?.overview
}

@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("releaseDate")
fun TextView.setReleaseDate(movie: Movie?) {
    text = movie?.let {
        context.getString(R.string.release_date,
            movie.releaseDate?.let { releaseDate -> formatReleaseDate(releaseDate) })
    }
}

@BindingAdapter("voteAverage")
fun TextView.setVoteAverage(movie: Movie?) {
    text = movie?.let{
        movie.voteAverage.toString()
    }
}

