package com.example.movamovieapp.screen.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.adapters.NotificationAdapter
import com.example.movamovieapp.databinding.FragmentNotificationBinding
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()
    private  val adapter= NotificationAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovie()
        observe()
        binding.rvnotification.adapter = adapter




        adapter.onClick = {
            val action = NotificationFragmentDirections.actionNotificationFragmentToDetailFragment(it.id)
            findNavController().navigate(action)
        }
        binding.imageView17.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    private fun observe() {
        viewModel.allmovies.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                binding.rvnotification.visible()
                adapter.updateList(list)

            }
        }


    }
}