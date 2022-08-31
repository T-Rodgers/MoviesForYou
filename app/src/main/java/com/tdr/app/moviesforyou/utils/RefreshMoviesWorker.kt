package com.tdr.app.moviesforyou.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tdr.app.moviesforyou.database.MovieRepository
import com.tdr.app.moviesforyou.database.getDatabase
import retrofit2.HttpException

class RefreshMoviesWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORKER_NAME = "RefreshMovieWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val movieRepository = MovieRepository(database)
        return try {
//            database.movieDao.deleteAllMovies()
            movieRepository.refreshMovies()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}