package com.example.movamovieapp.screen.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentEditProfileBinding


class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    binding.imageViewedit.setImageURI(uri)
                }
            }
        }
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }



        binding.imageView57popback.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.countryCodePicker.registerCarrierNumberEditText(binding.editTextPhone)


        val genderList = listOf("Male", "Female", "Other")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            genderList
        )
        binding.autoCompleteGender.setAdapter(adapter)

        binding.autoCompleteGender.setDropDownBackgroundResource(R.color.gray)


        binding.autoCompleteGender.setOnItemClickListener { parent, view, position, id ->
            val selectedGender = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "Selected Gender: $selectedGender", Toast.LENGTH_SHORT)
                .show()
        }


        val countries = listOf(
            "Afghanistan",
            "Albania",
            "Algeria",
            "Andorra",
            "Angola",
            "Antigua and Barbuda",
            "Argentina",
            "Azerbaijan",
            "Australia",
            "Austria",
            "Bahamas",
            "Bahrain",
            "Bangladesh",
            "Barbados",
            "Belarus",
            "Turkey"
        )
        val countryadapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, countries)

        binding.autoCompleteCountry.setAdapter(countryadapter)

        binding.autoCompleteCountry.setDropDownBackgroundResource(R.color.gray)
        binding.autoCompleteCountry.setOnItemClickListener { parent, view, position, id ->
            val selectedCountry = parent.getItemAtPosition(position).toString()
            Toast.makeText(
                requireContext(),
                "Selected Country: $selectedCountry",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}