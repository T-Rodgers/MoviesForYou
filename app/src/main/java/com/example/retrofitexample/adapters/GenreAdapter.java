package com.example.retrofitexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitexample.R;
import com.example.retrofitexample.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder>{

    private List<Genre> genreList;
    private Context context;

    public GenreAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.genre_list_item, parent, false);

        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {

        Genre genre = genreList.get(position);

        holder.genreButton.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        if (genreList != null) {

            return genreList.size();
        }
        return 0;
    }

    public void setGenreList(List<Genre> genres) {
        genreList = genres;
        notifyDataSetChanged();
    }
}
