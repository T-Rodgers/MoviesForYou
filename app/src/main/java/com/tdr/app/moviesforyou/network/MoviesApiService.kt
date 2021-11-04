package com.tdr.app.moviesforyou.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/"
enum class MovieApiFilter(val value: String){
    SHOW_POPULAR("popular"),
    SHOW_TOP_RATED("top_rated")

}

/** Build the Moshi object to use with Retrofit. */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/** Builds retrofit object using moshi converter */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_MOVIE_DB_URL)
    .build()

interface MoviesApiService {

    @GET("popular")
    suspend fun getMovies(@Query("api_key") type: String): MoviesResponse

    @GET("{movie_id}/videos")
    suspend fun getTrailers(@Path("movie_id")id: Int,@Query("api_key")type: String): TrailersResponse
}

object MoviesApi{
    val retrofitService : MoviesApiService by lazy { retrofit.create(MoviesApiService::class.java) }
}