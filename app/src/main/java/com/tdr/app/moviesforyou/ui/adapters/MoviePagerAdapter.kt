package com.tdr.app.moviesforyou.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tdr.app.moviesforyou.databinding.ListItemBinding
import com.tdr.app.moviesforyou.model.Movie

class MoviePagerAdapter(private val clickListener: MovieItemClickListener) :
    PagingDataAdapter<Movie, MoviePagerAdapter.MoviePagerViewHolder>(MovieDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagerViewHolder {
        return MoviePagerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MoviePagerViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            if (item != null) {
                clickListener.onClick(item)
            }
        }
        if (item != null) {
            holder.bind(item)
        }
    }

    class MoviePagerViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MoviePagerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(inflater, parent, false)
                return MoviePagerViewHolder(binding)
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
