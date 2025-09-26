package com.example.movamovieapp.screen.profile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
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
import java.io.File


class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    private lateinit var sharedPreferences: SharedPreferences

    private val firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance()


    private var selectedImageUri: String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = getUserSharedPreferences()
        setupImagePicker()
        loadUserData()


        binding.buttonEdit2.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
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

        binding.updatebutton.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2000)
                binding.animationView22.gone()
                saveUserData()

            }
        }
    }

    private fun setupImagePicker() {
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult

                requireContext().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val path = saveImageToInternalStorage(uri)
                if (path != null) {
                    selectedImageUri = path

                    val bitmap = android.graphics.BitmapFactory.decodeFile(path)
                    binding.editimage.setImageBitmap(bitmap)

                    sharedPreferences.edit().putString("image", path).apply()
                }
            }
        }
    }


    private fun getUserSharedPreferences(): SharedPreferences {
        val currentUserId = firebaseAuth.currentUser?.uid ?: "guest"
        return requireContext().getSharedPreferences(
            "MyPrefs_$currentUserId",
            Activity.MODE_PRIVATE
        )
    }
    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = File(requireContext().filesDir, "profile_image.jpg")
            val outputStream = file.outputStream()
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun loadUserData(){
        binding.editnametextinput.setText(sharedPreferences.getString("Fullname", ""))
        binding.nicknameedittext.setText(sharedPreferences.getString("nickname", ""))
        binding.emailedittext.setText(sharedPreferences.getString("email", ""))

        binding.autoCompleteCountry.setText(sharedPreferences.getString("country", ""),false)
        binding.autoCompleteGender.setText(sharedPreferences.getString("gender", ""),false)
        binding.editTextPhone.setText(sharedPreferences.getString("phone", ""))


        val savedPath = sharedPreferences.getString("image", null)
        val file = savedPath?.let { File(it) }
        if (file?.exists() == true) {
            binding.editimage.setImageURI(Uri.fromFile(file))
            selectedImageUri = savedPath
        } else {
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
            selectedImageUri?.let { putString("image", it) }
            apply()

            Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume() {
        super.onResume()
        loadUserData()
    }



}
