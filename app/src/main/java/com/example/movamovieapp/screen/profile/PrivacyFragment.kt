package com.example.movamovieapp.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentPrivacyBinding


class PrivacyFragment : BaseFragment<FragmentPrivacyBinding>(FragmentPrivacyBinding::inflate){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView57.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}