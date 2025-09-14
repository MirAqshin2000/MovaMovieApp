package com.example.movamovieapp.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentEditProfileNotificationBinding

class EditProfileNotificationFragment : BaseFragment<FragmentEditProfileNotificationBinding>(FragmentEditProfileNotificationBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView30.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}