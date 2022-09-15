package com.tdr.app.moviesforyou.db

import android.content.Context
import androidx.room.Room


/**
 * Singleton Object for Database for dependency injection.
 */
object LocalDB {

    fun createMovieDao(context: Context): MovieDao {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java, "movies.db"
        ).build().movieDao()
    }
}