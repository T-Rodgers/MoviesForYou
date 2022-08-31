package com.tdr.app.moviesforyou.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tdr.app.moviesforyou.network.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getAllPopularMovies(): LiveData<List<DatabaseMovie>>

    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    fun sortByRating(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movies: DatabaseMovie)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}