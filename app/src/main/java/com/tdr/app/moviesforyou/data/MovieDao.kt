package com.tdr.app.moviesforyou.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getPopularMoviesFlow(): Flow<List<Movie>>

    @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
    fun sortByRatingFlow(): Flow<List<Movie>>
}