package com.tdr.app.moviesforyou.networking;

import androidx.lifecycle.MutableLiveData;


import com.tdr.app.moviesforyou.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private static MoviesRepository moviesRepository;

    public static MoviesRepository getInstance() {
        if (moviesRepository == null) {
            moviesRepository = new MoviesRepository();
        }
        return moviesRepository;
    }

    private MoviesService moviesService;

    public MoviesRepository() {
        moviesService = RetrofitClient.getRetrofitInstance().create(MoviesService.class);
    }

    public MutableLiveData<MoviesResponse> getGenres(String key) {
        MutableLiveData<MoviesResponse> movieData = new MutableLiveData<>();
        moviesService.getAllGenres(key).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }


    public MutableLiveData<MoviesResponse> getPopularMovies(String key, String popularity, int page, String genreId) {
        MutableLiveData<MoviesResponse> movieData = new MutableLiveData<>();
        moviesService.getPopularMoviesByGenre(key, popularity, page, genreId).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }

    public MutableLiveData<MoviesResponse> getTopRatedMovies(String key) {
        MutableLiveData<MoviesResponse> movieData = new MutableLiveData<>();
        moviesService.getTopRatedMovies(key).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    movieData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                movieData.setValue(null);
            }
        });
        return movieData;
    }
}
