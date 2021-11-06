package com.tdr.app.moviesforyou.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.databinding.FragmentMovieDetailsBinding
import com.tdr.app.moviesforyou.details.DetailsViewModel
import com.tdr.app.moviesforyou.details.DetailsViewModelFactory

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)

        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).selectedMovie
        val factory = DetailsViewModelFactory(movie, application)

        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_trailer_action -> viewModel.initializeTrailer(requireContext())
        }
        return super.onOptionsItemSelected(item)

    }

}