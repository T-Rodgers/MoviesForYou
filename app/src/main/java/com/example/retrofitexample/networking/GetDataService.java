package com.example.retrofitexample.networking;

import com.example.retrofitexample.model.Genre;
import com.example.retrofitexample.model.GenreWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("genre/movie/list")
    Call<GenreWrapper> getAllGenres(@Query("api_key") String key);
}
