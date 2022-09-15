package com.tdr.app.moviesforyou.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tdr.app.moviesforyou.model.Movie
import com.tdr.app.moviesforyou.network.MoviesApiService
import com.tdr.app.moviesforyou.ui.viewmodels.API_KEY
import timber.log.Timber

private const val MOVIEDB_STARTING_PAGE_INDEX = 1

class MoviePagingSource(private val service: MoviesApiService) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIEDB_STARTING_PAGE_INDEX
        val apiKey = API_KEY
        return try {

            val response = service.getPopularMovies(apiKey, position)
            val movies = response.results
            Timber.i("Current Page Shown: $position")
            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIEDB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
