package com.tdr.app.moviesforyou.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tdr.app.moviesforyou.network.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getAllPopularMovies(): LiveData<List<DatabaseMovie>>

    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    fun sortByRating(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getPopularMoviesFlow(): Flow<List<Movie>>

    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    fun sortByRatingFlow(): Flow<List<Movie>>
}