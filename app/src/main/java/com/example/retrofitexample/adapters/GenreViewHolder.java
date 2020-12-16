package com.example.retrofitexample.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitexample.R;
import com.google.android.material.button.MaterialButton;

public class GenreViewHolder extends RecyclerView.ViewHolder {

    public MaterialButton genreButton;

    public GenreViewHolder(@NonNull View itemView) {
        super(itemView);

        genreButton = itemView.findViewById(R.id.genre_button);


    }
}
