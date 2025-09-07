package com.example.movamovieapp.screen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.adapters.FilmsAdapter
import com.example.movamovieapp.databinding.FragmentViewAllBinding
import com.example.movamovieapp.enum1.CategoryMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAllFragment : Fragment() {
    private lateinit var binding: FragmentViewAllBinding
    private val args: ViewAllFragmentArgs by navArgs()
    private lateinit var adapter: FilmsAdapter
    private val viewModel: VieweAllViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= FilmsAdapter{movieId->
            val action = ViewAllFragmentDirections.actionViewAllFragmentToDetailFragment(movieId)
            findNavController().navigate(action)

        }
        observe()
        val categoryEnum= CategoryMovie.fromApiValue(args.category )


        binding.rvallmovie.adapter = adapter
        binding.textViewheader.text = categoryEnum.displayName

        viewModel.getMovies(categoryEnum.apiValue)
binding.imageView16back.setOnClickListener {
    findNavController().popBackStack()
}


    }
    private fun observe(){
        viewModel.movies.observe(viewLifecycleOwner){
            adapter.updateList(it)
        }

    }
}