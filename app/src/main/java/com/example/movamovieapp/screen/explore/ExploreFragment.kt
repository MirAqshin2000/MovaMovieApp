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
import com.example.movamovieapp.adapters.FilmsAdapter
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var adapter : FilmsAdapter
    private val viewModel: ExploreViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= FilmsAdapter { movieId ->
            val action = ExploreFragmentDirections.actionExploreFragmentToDetailFragment(movieId)
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
                }else{
                    viewModel.getMovie()
                }
            }

        }





    }

    private fun observe() {
        viewModel.allmovies.observe(viewLifecycleOwner) {list->
            if (list.isNotEmpty()) {
                binding.recyclerView2.visible()
                binding.imageView4.gone()
                binding.textView20Notfound.gone()
                binding.textView21sorry.gone()
                adapter.updateList(list)

            }else{
                binding.recyclerView2.gone()
                binding.imageView4.visible()
                binding.textView20Notfound.visible()
                binding.textView21sorry.visible()
            }
        }
     viewModel.searchmovie.observe(viewLifecycleOwner) {
         if (it.isNotEmpty()) {
             binding.recyclerView2.visible()
             binding.imageView4.gone()
             binding.textView20Notfound.gone()
             binding.textView21sorry.gone()
             adapter.updateList(it)

         }else{
             binding.recyclerView2.gone()
             binding.imageView4.visible()
             binding.textView20Notfound.visible()
             binding.textView21sorry.visible()
         }
     }

    }
}