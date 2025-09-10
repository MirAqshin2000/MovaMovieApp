package com.example.movamovieapp.screen.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.adapters.FilmsAdapter
import com.example.movamovieapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private fun createAdapter() = FilmsAdapter { movieId ->
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private val popularAdapter by lazy { createAdapter() }
    private val nowPlayingAdapter by lazy { createAdapter() }
    private val top10Adapter by lazy { createAdapter() }
    private val upcomingAdapter by lazy { createAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeViewModel()


        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
        viewModel.getUpcomingMovies()
        viewModel.getNowPlayingMovies()
        viewModel.loadFeaturedMovie()
        setonClickListener()

    }

    private fun setupRecyclerViews() {
        binding.rvpopular.adapter = popularAdapter
        binding.recyclerViewtop10.adapter = top10Adapter
        binding.rvnewreleases.adapter = nowPlayingAdapter
        binding.rvupcomig.adapter = upcomingAdapter
    }

    private fun setonClickListener() {
        binding.top10buttonSeeAll.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(
                "top_rated"
            )
            findNavController().navigate(action)
        }
        binding.buttonSeeAllnewreleases.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(
                "now_playing"
            )
            findNavController().navigate(action)

        }
        binding.buttonSeeAllPopular.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(
                "popular"
            )
            findNavController().navigate(action)
        }
        binding.buttonSeeAllUpcoming.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToViewAllFragment2(
                "upcoming"
            )
            findNavController().navigate(action)
        }

        binding.imageView17search2.setOnClickListener {
           findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToExploreFragment())
        }
        binding.imageView16notification2.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotificationFragment())
        }

    }

    private fun observeViewModel() {
        viewModel.popularMovies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Success -> {
                    val sortedList = state.movieList.sortedByDescending { it.voteAverage ?: 0.0 }
                    popularAdapter.updateList(sortedList)

                }

                is MovieState.Error -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()

                is MovieState.Loading -> {}
            }
        }


        viewModel.top10Movies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Success -> {
                    val sortedList = state.movieList.sortedByDescending { it.voteAverage ?: 0.0 }
                    top10Adapter.updateList(sortedList)


                }

                is MovieState.Error -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()

                is MovieState.Loading -> {}
            }
        }

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Success -> {

                    val sortedList = state.movieList.sortedByDescending { it.voteAverage ?: 0.0 }
                    nowPlayingAdapter.updateList(sortedList)


                }

                is MovieState.Error -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()

                is MovieState.Loading -> {}
            }
        }

        viewModel.upcomingMovies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Success -> {
                    val sortedList = state.movieList.sortedByDescending { it.voteAverage ?: 0.0 }

                    upcomingAdapter.updateList(sortedList)


                }

                is MovieState.Error -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()

                is MovieState.Loading -> {}
            }
        }

        viewModel.featuredMovie.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                binding.textViewTitle.text = it.title
                binding.textViewOverview.text = it.releaseDate
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500${it.posterPath}")
                    .into(binding.reklamFlimImage)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
        }
    }
}
