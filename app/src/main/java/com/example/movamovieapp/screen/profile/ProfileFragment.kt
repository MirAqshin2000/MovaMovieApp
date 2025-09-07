package com.example.movamovieapp.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movamovieapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
 binding.switch1.setOnCheckedChangeListener { _, isChecked ->

     viewModel.setDarkMode(isChecked)
    }

    }
    private fun observe(){
        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            binding.switch1.isChecked = isDarkMode
        }
    }

}