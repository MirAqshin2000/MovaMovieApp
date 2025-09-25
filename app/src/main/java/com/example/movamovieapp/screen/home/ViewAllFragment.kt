package com.example.movamovieapp.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.adapters.FilmsAdapter
import com.example.movamovieapp.databinding.FragmentViewAllBinding
import com.example.movamovieapp.enum1.CategoryMovie
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAllFragment : BaseFragment<FragmentViewAllBinding>(FragmentViewAllBinding::inflate) {

    private val args: ViewAllFragmentArgs by navArgs()
    private lateinit var adapter: FilmsAdapter
    private val viewModel: VieweAllViewModel by viewModels()
    private var category: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()




        adapter = FilmsAdapter { movieId ->
            val action = ViewAllFragmentDirections.actionViewAllFragmentToDetailFragment(movieId)
            findNavController().navigate(action)

        }

        binding.rvallmovie.adapter = adapter

        val categoryEnum = CategoryMovie.fromApiValue(args.category)
        binding.textViewheader.text = categoryEnum.displayName




        binding.imageView17top.setOnClickListener {
            if (binding.searchLayout22.visibility == View.VISIBLE) {
                binding.searchLayout22.gone()
                binding.imageView16back.visible()
                binding.textViewheader.visible()
            } else {
                binding.searchLayout22.visible()
                binding.imageView16back.gone()
                binding.textViewheader.gone()

            }
        }

        binding.searchedittext222.addTextChangedListener {
            viewModel.searchMovies(it.toString())
        }
        viewModel.getMovies(categoryEnum.apiValue)


        binding.imageView16back.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun observe() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }

    }
}