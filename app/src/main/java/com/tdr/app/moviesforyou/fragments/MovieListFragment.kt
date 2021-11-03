package com.tdr.app.moviesforyou.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.adapters.MovieListAdapter
import com.tdr.app.moviesforyou.databinding.FragmentMovieListBinding
import com.tdr.app.moviesforyou.network.MoviesViewModel

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MovieListAdapter(MovieListAdapter.MovieItemClickListener {
            viewModel.displayMovieItemDetails(it)
        })

        // TODO: (1) Set correct titles
        // TODO: (2) Implement switching between top rated and popular
        // TODO: (3) Add review list
        // TODO: (4) Add trailer button
        // TODO: (5) Implement Changing from popular to top-rated
        // TODO: (6) Learn Pagination
        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        })

        binding.recyclerView.adapter = adapter

        return binding.root
    }

}