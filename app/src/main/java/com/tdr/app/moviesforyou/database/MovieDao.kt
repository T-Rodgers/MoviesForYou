package com.tdr.app.moviesforyou.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM databasemovie ORDER BY popularity DESC")
    fun getAllPopularMovies(): LiveData<List<DatabaseMovie>>

    @Query("SELECT * FROM databasemovie ORDER BY voteAverage DESC")
    fun getAllTopRatedMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: DatabaseMovie)
}