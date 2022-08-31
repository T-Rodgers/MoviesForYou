package com.tdr.app.moviesforyou.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.databinding.FragmentMovieDetailsBinding
import com.tdr.app.moviesforyou.details.DetailsViewModel
import com.tdr.app.moviesforyou.details.DetailsViewModelFactory
import timber.log.Timber

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var viewModel : DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application

        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).selectedMovie

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        binding.lifecycleOwner = this
        binding.movie = movie

        val factory = DetailsViewModelFactory(movie, application)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_trailer_action -> launchTrailer()
        }
        return super.onOptionsItemSelected(item)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun launchTrailer() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetwork
        if(networkInfo != null){
            viewModel.initializeTrailer(requireContext())
        } else {
            Snackbar.make(
                requireView(),
                "No available network",
                LENGTH_INDEFINITE
            )
                .setAction(android.R.string.ok) {
                }
                .show()
        }

        Timber.i("${networkInfo?.networkHandle}")

    }
}