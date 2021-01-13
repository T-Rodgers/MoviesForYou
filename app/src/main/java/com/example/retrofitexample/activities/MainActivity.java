package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofitexample.R;
import com.example.retrofitexample.adapters.GenreAdapter;
import com.example.retrofitexample.model.Genre;
import com.example.retrofitexample.model.ResponseWrapper;
import com.example.retrofitexample.networking.GetDataService;
import com.example.retrofitexample.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.retrofitexample.utils.Constants.API_KEY;
import static com.example.retrofitexample.utils.Constants.EXTRA_SELECTED_GENRE_ID;

public class MainActivity extends AppCompatActivity implements GenreAdapter.OnGenreClickHandler {

    private GenreAdapter adapter;
    ProgressDialog progressDialog;
    private List<Genre> genres;
    private Call<ResponseWrapper> call;
    private GetDataService service;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Genres...");
        progressDialog.show();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new GenreAdapter(MainActivity.this, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

        service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);

        call = service.getAllGenres(API_KEY);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                progressDialog.dismiss();
                if (response != null) {
                genres = new ArrayList<>();
                genres = response.body().getGenres();
                adapter.setGenreList(genres);

                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }

    @Override
    public void onClick(Genre genre) {
        Intent movieListIntent = new Intent(this, MovieListActivity.class);
        movieListIntent.putExtra(EXTRA_SELECTED_GENRE_ID, genre.getId());
        startActivity(movieListIntent);
    }
}