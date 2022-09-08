package com.tdr.app.moviesforyou.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tdr.app.moviesforyou.data.Movie
import com.tdr.app.moviesforyou.databinding.ListItemBinding

class MovieListAdapter(private val clickListener: MovieItemClickListener) :
    ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(item)
        }
        holder.bind(item)
    }

    class MovieViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(inflater, parent, false)
                return MovieViewHolder(binding)
            }
        }

    }

    class MovieItemClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }

    class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
