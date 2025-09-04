package com.example.movamovieapp.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentLetsBinding


class LetsFragment : BaseFragment<FragmentLetsBinding>(FragmentLetsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(LetsFragmentDirections.actionLetsFragmentToLoginFragment())

        }
        binding.textView3signup.setOnClickListener {
            findNavController().navigate(LetsFragmentDirections.actionLetsFragmentToRegisterFragment())

        }

        binding.imageView4back.setOnClickListener {
            findNavController().popBackStack()
        }
    }



}