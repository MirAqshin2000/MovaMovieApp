package com.example.movamovieapp.screen.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.adapters.NotificationAdapter
import com.example.movamovieapp.databinding.FragmentNotificationBinding
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment :BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::inflate) {
    private val viewModel: NotificationViewModel by viewModels()
    private  val adapter= NotificationAdapter()


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