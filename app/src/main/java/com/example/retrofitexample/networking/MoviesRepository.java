package com.example.retrofitexample.networking;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitexample.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

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

//    @Query("api_key") String key,
//    @Query("sort_by") String popularity,
//    @Query("page") int page,
//    @Query("with_genres") String genreId);

    public MutableLiveData<MoviesResponse> getMovies(String key, String popularity, int page, String genreId) {
        MutableLiveData<MoviesResponse> movieData = new MutableLiveData<>();
        moviesService.getPopularMovies(key, popularity, page, genreId).enqueue(new Callback<MoviesResponse>() {
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
