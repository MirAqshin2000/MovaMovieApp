package com.example.movamovieapp.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentEditProfileBinding


class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextPhone)

//        binding.buttonNext.setOnClickListener {
//            val fullNumber = binding.countryCodePicker.fullNumberWithPlus
//            Toast.makeText(requireContext(), "Full Number: $fullNumber", Toast.LENGTH_SHORT).show()
//        }

    }
}