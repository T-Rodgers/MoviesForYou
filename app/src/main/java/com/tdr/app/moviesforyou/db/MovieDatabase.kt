package com.tdr.app.moviesforyou.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tdr.app.moviesforyou.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao
}


