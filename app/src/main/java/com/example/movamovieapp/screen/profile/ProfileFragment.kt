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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentProfileBinding
import com.example.movamovieapp.util.LocalHelper
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

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

        pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    binding.imageView25.setImageURI(uri)
                }
            }
        }
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }


        val prefs = SharedPrefManager(requireContext())

        binding.textView12email.text = prefs.getUserEmail()

        binding.materialCardView.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSubscribeFragment())
            }
        }
        binding.constraintLayoutNotifications.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileNotificationFragment())
            }
        }
        binding.constraintLayoutEditProfile.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
        }

        binding.constrainthelpcenter.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHelpFragment())
            }
        }

        binding.constraintLayoutprivacy.setOnClickListener {
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
        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            binding.switch1.isChecked = isDarkMode
        }


        binding.switch1.setOnCheckedChangeListener { _, isChecked ->

            viewModel.setDarkMode(isChecked)
        }
    }
}







