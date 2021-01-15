package com.tdr.app.moviesforyou.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdr.app.moviesforyou.R;
import com.tdr.app.moviesforyou.adapters.MovieAdapter;
import com.tdr.app.moviesforyou.model.Movie;
import com.tdr.app.moviesforyou.viewmodels.MoviesViewModel;

import java.util.List;

import static com.tdr.app.moviesforyou.utils.Constants.EXTRA_SELECTED_GENRE_ID;


public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String genreId;
    private MovieAdapter adapter;
    private List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        recyclerView = findViewById(R.id.movie_recyclerview);

        Intent genreData = getIntent();
        if (genreData != null) {
            genreId = genreData.getStringExtra(EXTRA_SELECTED_GENRE_ID);
        }

        initiateViewModel();
        initiateRecyclerView();

    }

    private void initiateRecyclerView() {

        if (adapter == null) {
            adapter = new MovieAdapter(this);
            recyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns(this)));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 120;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    private void initiateViewModel() {
        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getPopularMoviesOfGenre(genreId);
        moviesViewModel.getMoviesRepository().observe(this, moviesResponse -> {
            movies = moviesResponse.getMovies();
            adapter.setMovieList(movies);
        });
    }
}