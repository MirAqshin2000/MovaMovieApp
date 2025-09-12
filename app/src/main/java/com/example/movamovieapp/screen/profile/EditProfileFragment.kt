package com.example.movamovieapp.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentEditProfileBinding


class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextPhone)




        val genderList = listOf("Male", "Female", "Other")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            genderList)
        binding.autoCompleteGender.setAdapter(adapter)

        binding.autoCompleteGender.setDropDownBackgroundResource(R.color.gray)


binding.autoCompleteGender.setOnItemClickListener { parent, view, position, id ->
    val selectedGender = parent.getItemAtPosition(position).toString()
    Toast.makeText(requireContext(), "Selected Gender: $selectedGender", Toast.LENGTH_SHORT).show()
}

    }
}