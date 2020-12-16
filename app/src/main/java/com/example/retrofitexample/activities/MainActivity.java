package com.example.retrofitexample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofitexample.R;
import com.example.retrofitexample.adapters.GenreAdapter;
import com.example.retrofitexample.model.Genre;
import com.example.retrofitexample.model.GenreWrapper;
import com.example.retrofitexample.networking.GetDataService;
import com.example.retrofitexample.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GenreAdapter adapter;
    ProgressDialog progressDialog;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Genres...");
        progressDialog.show();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new GenreAdapter(MainActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);

        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);

        Call<GenreWrapper> call = service.getAllGenres("API_KEY");
        call.enqueue(new Callback<GenreWrapper>() {
            @Override
            public void onResponse(Call<GenreWrapper> call, Response<GenreWrapper> response) {
                progressDialog.dismiss();
                adapter.setGenreList(response.body().getGenres());
            }

            @Override
            public void onFailure(Call<GenreWrapper> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }

}