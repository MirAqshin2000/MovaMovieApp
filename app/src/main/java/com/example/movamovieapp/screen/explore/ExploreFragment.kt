package com.example.movamovieapp.screen.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movamovieapp.databinding.FragmentExploreBinding
import kotlinx.coroutines.delay
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.adapters.FilmsAdapter
import com.example.movamovieapp.adapters.MoreLikeAdapter
import com.example.movamovieapp.model.Result
import com.example.movamovieapp.screen.detail.DetailFragmentDirections
import com.example.movamovieapp.util.gone

import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class ExploreFragment :BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {
    private val adapter = MoreLikeAdapter()
    private val viewModel: ExploreViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        adapter= MoreLikeAdapter { movieId ->
//            var action = ExploreFragmentDirections.actionExploreFragmentToDetailFragment(movieId)
//            findNavController().navigate(action)
//        }

        adapter.onItemClickListener = {
            val action = ExploreFragmentDirections.actionExploreFragmentToDetailFragment(it.id)
            findNavController().navigate(action)
        }



        binding.recyclerView2.adapter = adapter
        observe()
        viewModel.getMovie()




        binding.searchtext.addTextChangedListener { text ->
            lifecycleScope.launch {
                val query = text.toString().trim()
                if (query.isNotEmpty()) {
                    delay(1000)
                    viewModel.searchMovies(query)
                } else {
                    viewModel.isSearching = false
                    updateRecyclerView(viewModel.allmovies.value ?: emptyList())
                }
            }

        }


    }

    private fun observe() {
        viewModel.allmovies.observe(viewLifecycleOwner) { list ->
            if (!viewModel.isSearching) updateRecyclerView(list)
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { list ->
            if (viewModel.isSearching) updateRecyclerView(list)
        }
    }

    private fun updateRecyclerView(list: List<Result>) {
        if (list.isNotEmpty()) {
            binding.recyclerView2.visible()
            binding.imageView4.gone()
            binding.textView20Notfound.gone()
            binding.textView21sorry.gone()
            adapter.updateMore(list)
        } else {
            binding.recyclerView2.gone()
            binding.imageView4.visible()
            binding.textView20Notfound.visible()
            binding.textView21sorry.visible()
        }
    }

}