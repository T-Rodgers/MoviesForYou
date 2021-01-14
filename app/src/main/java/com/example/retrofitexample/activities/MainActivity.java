package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.retrofitexample.R;
import com.example.retrofitexample.adapters.GenreAdapter;
import com.example.retrofitexample.model.Genre;
import com.example.retrofitexample.model.MoviesResponse;
import com.example.retrofitexample.networking.MoviesService;
import com.example.retrofitexample.networking.RetrofitClient;
import com.example.retrofitexample.viewmodels.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.retrofitexample.utils.Constants.API_KEY;
import static com.example.retrofitexample.utils.Constants.EXTRA_SELECTED_GENRE_ID;

public class MainActivity extends AppCompatActivity implements GenreAdapter.OnGenreClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Genre> genres;
    private GenreAdapter adapter;
    private RecyclerView recyclerView;
    private MoviesViewModel moviesViewModel;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.genre_recyclerview);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Genres...");
        progressDialog.show();

        initiateViewModel();
        initiateRecyclerView();
    }

    private void initiateViewModel() {
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getGenres();
        moviesViewModel.getMoviesRepository().observe(this, moviesResponse -> {
            genres = moviesResponse.getGenres();
            adapter.setGenreList(genres);
            progressDialog.dismiss();
        });
    }

    private void initiateRecyclerView() {
        if (adapter == null) {
            adapter = new GenreAdapter(this, this);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(Genre genre) {
        Intent movieListIntent = new Intent(this, MovieListActivity.class);
        movieListIntent.putExtra(EXTRA_SELECTED_GENRE_ID, genre.getId());
        startActivity(movieListIntent);
    }
}