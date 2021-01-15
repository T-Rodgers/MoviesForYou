package com.tdr.app.moviesforyou.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdr.app.moviesforyou.R;
import com.tdr.app.moviesforyou.adapters.GenreAdapter;
import com.tdr.app.moviesforyou.model.Genre;
import com.tdr.app.moviesforyou.viewmodels.MoviesViewModel;

import java.util.List;

import static com.tdr.app.moviesforyou.utils.Constants.EXTRA_SELECTED_GENRE_ID;


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