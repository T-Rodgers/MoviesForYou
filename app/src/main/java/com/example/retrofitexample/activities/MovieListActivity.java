package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.retrofitexample.R;
import com.example.retrofitexample.adapters.MovieAdapter;
import com.example.retrofitexample.model.Genre;
import com.example.retrofitexample.model.Movie;
import com.example.retrofitexample.model.ResponseWrapper;
import com.example.retrofitexample.networking.GetDataService;
import com.example.retrofitexample.networking.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.retrofitexample.utils.Constants.API_KEY;
import static com.example.retrofitexample.utils.Constants.EXTRA_SELECTED_GENRE_ID;
import static com.example.retrofitexample.utils.Constants.POPULARITY_DESC;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private Genre genre;
    private String genreId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Intent genreData = getIntent();
        if (genreData != null) {
            genreId = genreData.getStringExtra(EXTRA_SELECTED_GENRE_ID);
        }

        recyclerView = findViewById(R.id.movie_recyclerview);
        adapter = new MovieAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseWrapper> call = service.getPopularMovies(API_KEY, POPULARITY_DESC, 1, genreId);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                if (response != null) {
                    adapter.setMovieList(response.body().getMovies());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

            }
        });
    }
}