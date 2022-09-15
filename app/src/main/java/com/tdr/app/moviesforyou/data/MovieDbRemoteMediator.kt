package com.tdr.app.moviesforyou.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tdr.app.moviesforyou.db.MovieDao
import com.tdr.app.moviesforyou.model.Movie
import com.tdr.app.moviesforyou.network.MoviesApiService

@OptIn(ExperimentalPagingApi::class)
class MovieDbRemoteMediator(private val service: MoviesApiService, private val database: MovieDao) :
    RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        TODO("Not yet implemented")
    }


}