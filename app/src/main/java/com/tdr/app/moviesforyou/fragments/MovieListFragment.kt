package com.tdr.app.moviesforyou.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tdr.app.moviesforyou.R
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

        return binding.root
    }
}