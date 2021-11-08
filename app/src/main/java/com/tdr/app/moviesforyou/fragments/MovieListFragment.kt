package com.tdr.app.moviesforyou.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MovieListAdapter(MovieListAdapter.MovieItemClickListener {
            viewModel.displayMovieItemDetails(it)
        })

        // TODO: (3) Add review list
        // TODO: (6) Learn Pagination
        viewModel.navigateToDetails.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        })

        binding.recyclerView.adapter = adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_popular -> viewModel.getPopularMovies()
            R.id.action_top_rated -> viewModel.getTopRatedMovies()
        }
        return super.onOptionsItemSelected(item)
    }

}