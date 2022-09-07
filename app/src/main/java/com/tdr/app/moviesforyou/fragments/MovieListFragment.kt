package com.tdr.app.moviesforyou.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.tdr.app.moviesforyou.R
import com.tdr.app.moviesforyou.adapters.MoviePagerAdapter
import com.tdr.app.moviesforyou.data.MoviePagingRepository
import com.tdr.app.moviesforyou.databinding.FragmentMovieListBinding
import com.tdr.app.moviesforyou.network.MoviePagingViewModel
import com.tdr.app.moviesforyou.network.MoviePagingViewModelFactory
import com.tdr.app.moviesforyou.network.MoviesApi
import com.tdr.app.moviesforyou.network.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    private val viewModel: MoviesViewModel by lazy {
        ViewModelProvider(this).get(MoviesViewModel::class.java)
    }

    private lateinit var pagingViewModel: MoviePagingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        val service = MoviesApi.retrofitService
        val pagingRepository = MoviePagingRepository(service)



        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        pagingViewModel =
            ViewModelProvider(this, MoviePagingViewModelFactory(pagingRepository)).get(
                MoviePagingViewModel::class.java
            )

//        val adapter = MovieListAdapter(MovieListAdapter.MovieItemClickListener {
//            viewModel.displayMovieItemDetails(it)
//        })

        val adapter = MoviePagerAdapter(MoviePagerAdapter.MovieItemClickListener {
            viewModel.displayMovieItemDetails(it)
        })

        binding.recyclerView.adapter = adapter

        setHasOptionsMenu(true)

        viewModel.navigateToDetails.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it)
                )
                viewModel.doneNavigating()
            }
        }

        lifecycleScope.launch {
            pagingViewModel.getMovieList().collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progress.isVisible = true
            else {
                binding.progress.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }

            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_popular -> viewModel.retrievePopularMovies()
            R.id.action_top_rated -> viewModel.sortByTopRated()
        }
        return super.onOptionsItemSelected(item)
    }
}