package com.example.retrofitexample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitexample.R;
import com.example.retrofitexample.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{

    private List<Genre> genreList;
    private Context context;
    private OnGenreClickHandler mClickHandler;

    public GenreAdapter(Context context, OnGenreClickHandler handler) {
        this.context = context;
        mClickHandler = handler;
    }

    public interface OnGenreClickHandler{
        void onClick(Genre genre);
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
        holder.genreTextView.setText(genre.getName());

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

    public class GenreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView genreTextView;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);

            genreTextView = itemView.findViewById(R.id.genre_text_view);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Genre genre = genreList.get(position);
            mClickHandler.onClick(genre);
        }
    }
}
