package com.example.retrofitexample.networking;

import com.example.retrofitexample.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("genre/movie/list")
    Call<MoviesResponse> getAllGenres(@Query("api_key") String key);


    @GET("discover/movie")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String key,
                                          @Query("sort_by") String popularity,
                                          @Query("page") int page,
                                          @Query("with_genres") String genreId);
}
