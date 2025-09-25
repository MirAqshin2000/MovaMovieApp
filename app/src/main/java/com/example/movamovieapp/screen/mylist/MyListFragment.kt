package com.example.movamovieapp.screen.mylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.MyListAdapter
import com.example.movamovieapp.databinding.FragmentMyListBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>(FragmentMyListBinding::inflate) {


    private val viewModel: MyListViewModel by viewModels()
    private val adapter = MyListAdapter()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMylist.adapter = adapter
        viewModel.getAllMovies()
        observe()



        binding.imageViewserch.setOnClickListener {
            if (binding.searchLayout2.visibility == View.VISIBLE) {
                binding.searchLayout2.gone()
                binding.imageView28.visible()
                binding.textView3List.visible()
            } else {
                binding.searchLayout2.visible()
                binding.imageView28.gone()
                binding.textView3List.gone()
            }
        }



        binding.searchedittext1.addTextChangedListener {
            val query = binding.searchedittext1.text.toString().trim()
            lifecycleScope.launch {
                delay(300)
                if (query.isNotEmpty()) {
                    viewModel.searchMovies(query)
                } else {
                    viewModel.getAllMovies()
                }
            }
        }


            adapter.onClick = { movie ->
                val action =
                    MyListFragmentDirections.actionMyListFragment2ToDetailFragment(movie.id.toInt())
                findNavController().navigate(action)
            }

            adapter.onItemClickListener = { movie ->

                val dialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)
                val alertDialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogView)
                    .create()

                val titleTextView = dialogView.findViewById<TextView>(R.id.tvTitle)
                val messageTextView = dialogView.findViewById<TextView>(R.id.tvMessage)
                val noButton = dialogView.findViewById<TextView>(R.id.btnNo)
                val yesButton = dialogView.findViewById<TextView>(R.id.btnYes)


                titleTextView.text = "Delete Movie"
                messageTextView.text = "Are you sure you want to delete this movie?"

                noButton.setOnClickListener {
                    alertDialog.dismiss()
                }
                yesButton.setOnClickListener {
                    viewModel.deleteMovie(movie.id.toInt())
                    alertDialog.dismiss()
                }
                alertDialog.show()


            }

        }

        private fun observe() {
            viewModel.movies.observe(viewLifecycleOwner) {
                adapter.updateList(it)

                if (it.isNullOrEmpty()) {
                    binding.imageView69list.visible()
                    binding.textView74notfound.visible()
                    binding.textView75sorry.visible()
                    binding.rvMylist.gone()
                } else {
                    binding.imageView69list.gone()
                    binding.textView74notfound.gone()
                    binding.textView75sorry.gone()
                    binding.rvMylist.visible()
                }
            }
        }


    }