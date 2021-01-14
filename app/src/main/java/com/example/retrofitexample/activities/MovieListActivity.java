package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.retrofitexample.R;
import com.example.retrofitexample.adapters.GenreAdapter;
import com.example.retrofitexample.adapters.MovieAdapter;
import com.example.retrofitexample.model.Genre;
import com.example.retrofitexample.model.Movie;
import com.example.retrofitexample.model.MoviesResponse;
import com.example.retrofitexample.networking.MoviesService;
import com.example.retrofitexample.networking.RetrofitClient;
import com.example.retrofitexample.viewmodels.MoviesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.retrofitexample.utils.Constants.API_KEY;
import static com.example.retrofitexample.utils.Constants.EXTRA_SELECTED_GENRE_ID;
import static com.example.retrofitexample.utils.Constants.POPULARITY_DESC;

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
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
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