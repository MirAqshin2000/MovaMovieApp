package com.example.movamovieapp.screen.profile

import android.app.Activity
import android.content.Intent
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
import com.example.movamovieapp.databinding.FragmentProfileBinding
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
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
        binding.imageView30.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }


     val prefs = SharedPrefManager(requireContext())

binding.textView12email.text=prefs.getUserEmail()

        binding.materialCardView.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSubscribeFragment())

            }
        }
    }

//    }
//    private fun observe(){
//        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
//            binding.switch1.isChecked = isDarkMode
//        }
    }

//        observe()
// binding.switch1.setOnCheckedChangeListener { _, isChecked ->

//     viewModel.setDarkMode(isChecked)
