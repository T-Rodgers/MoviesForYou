package com.tdr.app.moviesforyou.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdr.app.moviesforyou.model.MoviesResponse;
import com.tdr.app.moviesforyou.networking.MoviesRepository;

import static com.tdr.app.moviesforyou.utils.Constants.API_KEY;
import static com.tdr.app.moviesforyou.utils.Constants.POPULARITY_DESC;


public class MoviesViewModel extends ViewModel {
    private MutableLiveData<MoviesResponse> mutableLiveData;
    private MoviesRepository moviesRepository;

    public void getGenres() {
        if (mutableLiveData != null) {
            return;
        }
        moviesRepository = MoviesRepository.getInstance();
        mutableLiveData = moviesRepository.getGenres(API_KEY);
    }

    public void getPopularMoviesOfGenre(String genreId) {
        if (mutableLiveData != null) {
            return;
        }
        moviesRepository = MoviesRepository.getInstance();
        mutableLiveData = moviesRepository.getMovies(
                API_KEY,
                POPULARITY_DESC,
                1,
                genreId);
    }

    public LiveData<MoviesResponse> getMoviesRepository() {
        return mutableLiveData;
    }
}
