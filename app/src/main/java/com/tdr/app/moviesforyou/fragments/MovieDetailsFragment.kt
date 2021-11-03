package com.tdr.app.moviesforyou.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.databinding.FragmentMovieDetailsBinding
import com.tdr.app.moviesforyou.details.DetailsViewModel
import com.tdr.app.moviesforyou.details.DetailsViewModelFactory

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        binding.lifecycleOwner = this
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val factory = DetailsViewModelFactory(movie, application)

        binding.viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        return binding.root
    }
}