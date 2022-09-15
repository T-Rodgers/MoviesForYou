package com.tdr.app.moviesforyou.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tdr.app.moviesforyou.data.MovieRepository
import com.tdr.app.moviesforyou.db.LocalDB
import retrofit2.HttpException

class RefreshMoviesWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORKER_NAME = "RefreshMovieWorker"
    }

    override suspend fun doWork(): Result {
        val movieDao = LocalDB.createMovieDao(applicationContext)
        val movieRepository = MovieRepository(movieDao)
        return try {
            movieDao.deleteAllMovies()
            movieRepository.refreshMovies()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}