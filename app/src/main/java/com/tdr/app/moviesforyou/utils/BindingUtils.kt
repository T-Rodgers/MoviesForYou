package com.tdr.app.moviesforyou.utils

import android.os.Build
import android.view.View.GONE
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.data.Movie
import com.tdr.app.moviesforyou.network.MoviesApiStatus


private const val BASE_BACKDROP_PATH_URL = "https://image.tmdb.org/t/p/w780" // 780x439
private const val BASE_POSTER_PATH_URL = "https://image.tmdb.org/t/p/w300" // 300x450

/**
 * Binds recyclerview to List of movies returned from response.
 */
//@BindingAdapter("movieList")
//fun bindRecyclerView(recyclerView: RecyclerView, movies: List<Movie>?) {
//    val adapter = recyclerView.adapter as MovieListAdapter
//
//    movies?.let {
//        adapter.submitList(movies)
//    }
//
//}

//@BindingAdapter("statusImage")
//fun bindStatus(statusImageView: ImageView, status: MoviesApiStatus?) {
//    when (status) {
//        MoviesApiStatus.LOADING -> {
//            statusImageView.visibility = GONE
//        }
//        MoviesApiStatus.ERROR -> {
//            statusImageView.visibility = VISIBLE
//            statusImageView.setImageResource(R.drawable.ic_twotone_error_24)
//        }
//        else -> {
//            statusImageView.visibility = GONE
//        }
//    }
//}

//@BindingAdapter("statusIndicator")
//fun bindProgress(progress: ProgressBar, status: MoviesApiStatus?) {
//
//    when (status) {
//
//        MoviesApiStatus.LOADING -> {
//            progress.visibility = VISIBLE
//        }
//        MoviesApiStatus.ERROR -> {
//            progress.visibility = GONE
//        }
//        else -> {
//            progress.visibility = GONE
//
//        }
//    }
//}

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
            .error(R.drawable.ic_launcher_foreground)
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
    text = movie?.let {
        context.getString(R.string.release_date,
            movie.releaseDate?.let { releaseDate -> formatReleaseDate(releaseDate) })
    }
}

@BindingAdapter("status_message")
fun setStatusMessage(textView: TextView, status: MoviesApiStatus?){
    var message: String? = null
    when(status){
        MoviesApiStatus.LOADING -> {
            message = "Loading Movies"
        }
        MoviesApiStatus.ERROR -> { message = "Unable to load movies. Check the network."}
        else -> {textView.visibility = GONE }
    }
    textView.text = message
}

@BindingAdapter("voteAverage")
fun TextView.setVoteAverage(movie: Movie?) {
    text = movie?.let{
        movie.voteAverage.toString()
    }
}

