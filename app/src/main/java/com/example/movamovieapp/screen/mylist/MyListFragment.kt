package com.example.movamovieapp.screen.mylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.MyListAdapter
import com.example.movamovieapp.databinding.FragmentMyListBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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