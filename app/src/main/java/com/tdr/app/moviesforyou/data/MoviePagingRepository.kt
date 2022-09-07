package com.tdr.app.moviesforyou.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tdr.app.moviesforyou.network.MoviesApiService
import kotlinx.coroutines.flow.Flow


class MoviePagingRepository(private val service: MoviesApiService) {

    fun getAllMovies(): Flow<PagingData<Movie>> {


        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 21
            ),
            pagingSourceFactory = {
                MoviePagingSource(service)
            },
            initialKey = 1
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 42
    }

}