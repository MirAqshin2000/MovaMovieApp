package com.example.movamovieapp.mylist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.MyListAdapter
import com.example.movamovieapp.databinding.FragmentMyListBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : Fragment() {

    private lateinit var binding: FragmentMyListBinding

    private val viewModel: MyListViewModel by viewModels()
    private val adapter = MyListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMylist.adapter = adapter

        adapter.onItemClickListener = { movie ->

            viewModel.deleteMovie(movie.id)

        }
        viewModel.getAllMovies()
        observe()

    }

    private fun observe() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.updateList(it)

            if (it.isNullOrEmpty()) {
                binding.imageView29.visible()
                binding.rvMylist.gone()
            } else {
                binding.imageView29.gone()
                binding.rvMylist.visible()
            }
        }
    }



}