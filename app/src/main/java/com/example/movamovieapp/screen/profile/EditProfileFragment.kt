package com.example.movamovieapp.screen.profile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentEditProfileBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    private lateinit var sharedPreferences: SharedPreferences

    private var selectedImageUri: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    binding.editimage.setImageURI(uri)
                    selectedImageUri = uri.toString()
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
        binding.autoCompleteGender.threshold = 0


        binding.autoCompleteGender.setOnItemClickListener { parent, view, position, id ->
            val selectedGender = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "Selected Gender: $selectedGender", Toast.LENGTH_SHORT)
                .show()
            binding.autoCompleteGender.showDropDown()
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
        binding.autoCompleteCountry.threshold = 0

        binding.autoCompleteCountry.setDropDownBackgroundResource(R.color.gray)
        binding.autoCompleteCountry.setOnItemClickListener { parent, view, position, id ->
            val selectedCountry = parent.getItemAtPosition(position).toString()
            Toast.makeText(
                requireContext(),
                "Selected Country: $selectedCountry",
                Toast.LENGTH_SHORT
            ).show()
            binding.autoCompleteCountry.showDropDown()
        }
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)

        binding.updatebutton.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2000)
                binding.animationView22.gone()
                saveUserData()

            }
        }
    }
    private fun loadUserData(){
        binding.editnametextinput.setText(sharedPreferences.getString("Fullname", ""))
        binding.nicknameedittext.setText(sharedPreferences.getString("nickname", ""))
        binding.emailedittext.setText(sharedPreferences.getString("email", ""))

        binding.autoCompleteCountry.setText(sharedPreferences.getString("country", ""),false)
        binding.autoCompleteGender.setText(sharedPreferences.getString("gender", ""),false)
        binding.editTextPhone.setText(sharedPreferences.getString("phone", ""))



        val imageUri = sharedPreferences.getString("image", "")
        if (!imageUri.isNullOrEmpty()) {
            binding.editimage.setImageURI(android.net.Uri.parse(imageUri))
        }
        else{
            binding.editimage.setImageResource(R.drawable.default_profile_photo)
        }

    }
    private fun saveUserData(){
        sharedPreferences.edit().apply {
            putString("Fullname", binding.editnametextinput.text.toString())
            putString("nickname", binding.nicknameedittext.text.toString())
            putString("email", binding.emailedittext.text.toString())
            putString("country", binding.autoCompleteCountry.text.toString())
            putString("gender", binding.autoCompleteGender.text.toString())
            putString("phone", binding.editTextPhone.text.toString())
            putString("image", selectedImageUri?: "")
            apply()

            Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume() {
        super.onResume()
        loadUserData()
        }
    }
