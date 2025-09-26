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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentProfileBinding
import com.example.movamovieapp.util.LocalHelper
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    val sharedPreferences: SharedPreferences by lazy {

        requireContext().getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
    }

    private val viewModel: ProfileViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()


        // --- 1. Launcher register ---
        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    // --- 2. Şəkili internal storage-a kopyala ---
                    val path = saveImageToInternalStorage(uri)
                    if (path != null) {
                        sharedPreferences.edit()
                            .putString("image", path)
                            .apply()
                        binding.imageView25.setImageURI(Uri.fromFile(File(path)))
                    }
                }
            }
        }

        // --- 3. SharedPreferences-dən şəkil yüklə ---
        val savedPath = sharedPreferences.getString("image", null)
        val file = savedPath?.let { File(it) }
        if (file?.exists() == true) {
            binding.imageView25.setImageURI(Uri.fromFile(file))
        } else {
            binding.imageView25.setImageResource(R.drawable.default_profile_photo)
        }




        binding.buttonEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }


        val prefs = SharedPrefManager(requireContext())

        binding.textView12email.text = prefs.getUserEmail()

        binding.materialCardView.setOnClickListener {
            if (binding.materialCardView.isClickable) {
                binding.materialCardView.isClickable = false
            }
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSubscribeFragment())
            }
        }
        binding.constraintLayoutNotifications.setOnClickListener {
            if (binding.constraintLayoutNotifications.isClickable) {
                binding.constraintLayoutNotifications.isClickable = false
            }
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileNotificationFragment())
            }
        }
        binding.constraintLayoutEditProfile.setOnClickListener {

            if (binding.constraintLayoutEditProfile.isClickable) {
                binding.constraintLayoutEditProfile.isClickable = false
                lifecycleScope.launch {
                    binding.animationView22.visible()
                    delay(1500)
                    binding.animationView22.gone()
                    findNavController().navigate(
                        ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
                    )
                    binding.constraintLayoutEditProfile.isClickable = true
                }
            }
        }

        binding.constrainthelpcenter.setOnClickListener {
            if (binding.constrainthelpcenter.isClickable) {
                binding.constrainthelpcenter.isClickable = false
            }
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHelpFragment())
            }
        }

        binding.constraintLayoutprivacy.setOnClickListener {
            if (binding.constraintLayoutprivacy.isClickable) {
                binding.constraintLayoutprivacy.isClickable = false
            }
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToPrivacyFragment())
            }
        }
        binding.imageViewlogout.setOnClickListener {
            showLogoutBottomSheet()


        }


        binding.constraintLayoutLanguage.setOnClickListener {
            if (binding.constraintLayoutLanguage.isClickable) {
                binding.constraintLayoutLanguage.isClickable = false
            }
            val bottomSheet = BottomSheetDialog(requireContext())
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
            bottomSheet.setContentView(bottomSheetView)

            val pref = requireContext().getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)

            bottomSheetView.findViewById<View>(R.id.langEnglish).setOnClickListener {
                changeLanguage("en", pref)
                bottomSheet.dismiss()
            }
            bottomSheetView.findViewById<View>(R.id.langAzerbaijani).setOnClickListener {
                changeLanguage("az", pref)
                bottomSheet.dismiss()
            }
            bottomSheetView.findViewById<View>(R.id.langTurkish).setOnClickListener {
                changeLanguage("tr", pref)
                bottomSheet.dismiss()
            }
            bottomSheetView.findViewById<View>(R.id.langRussian).setOnClickListener {
                changeLanguage("ru", pref)
                bottomSheet.dismiss()
            }
            bottomSheet.show()

        }


    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                val file = File(requireContext().filesDir, "profile_image.jpg")
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                file.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    private fun showLogoutBottomSheet() {
        val bottomSheet = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_logout, null)
        bottomSheet.setContentView(bottomSheetView)

        val butonCancel = bottomSheetView.findViewById<View>(R.id.btnCancel)
        val btnLogout = bottomSheetView.findViewById<View>(R.id.btnLogout)

        butonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        btnLogout.setOnClickListener {
            bottomSheet.dismiss()
            val prefs = SharedPrefManager(requireContext())
            prefs.clearUser()

            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLetsFragment())
        }
        bottomSheet.show()
    }

    private fun changeLanguage(langCode: String, prefs: SharedPreferences) {
        prefs.edit().putString("app_language", langCode).apply()
        requireActivity().recreate()
    }


    private fun observe() {
        val isDark = sharedPreferences.getBoolean("dark_mode", false)
        binding.switch1.isChecked = isDark

        viewModel.setDarkMode(isDark, sharedPreferences)

        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            binding.switch1.isChecked = isDarkMode
        }

        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkMode(isChecked, sharedPreferences)
        }


    }

}







