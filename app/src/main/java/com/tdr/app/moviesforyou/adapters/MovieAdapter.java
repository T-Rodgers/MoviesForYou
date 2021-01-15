package com.tdr.app.moviesforyou.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tdr.app.moviesforyou.R;
import com.tdr.app.moviesforyou.model.Movie;

import java.util.List;

import static com.tdr.app.moviesforyou.utils.Constants.IMAGE_BASE_URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        if (movie.getPosterPath(IMAGE_BASE_URL) == null) {
            holder.movieTitle.setText(movie.getTitle());
            holder.movieTitle.setVisibility(View.VISIBLE);
            holder.moviePoster.setVisibility(View.INVISIBLE);
        } else {
            String imageUrl = movie.getPosterPath(IMAGE_BASE_URL);
            Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .override(342, 513)
                    .centerCrop()
                    .into(holder.moviePoster);
        }
    }

    @Override
    public int getItemCount() {
        if (movieList != null) {
            return movieList.size();
        } else {
            return 0;
        }
    }

    public void setMovieList(List<Movie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ImageView moviePoster;
        private final TextView movieTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movie_poster_imageview);
            movieTitle = itemView.findViewById(R.id.movie_title_textView);
        }
    }
}
