package com.example.retrofitexample.networking;

import com.example.retrofitexample.model.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("genre/movie/list")
    Call<ResponseWrapper> getAllGenres(@Query("api_key") String key);


    @GET("discover/movie")
    Call<ResponseWrapper> getPopularMovies(@Query("api_key") String key,
                                           @Query("sort_by") String popularity,
                                           @Query("page") int page,
                                           @Query("with_genres") String genreId);
}
