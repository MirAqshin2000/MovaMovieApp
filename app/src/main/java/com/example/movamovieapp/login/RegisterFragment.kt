package com.example.movamovieapp.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.databinding.FragmentRegisterBinding
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    private val viewModel: RegisterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerclick()
        observe()
        binding.imageView8arrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.signinregister.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    private fun registerclick() {
        binding.signupregister.setOnClickListener {
            val email = binding.editLoginemail.text.toString().trim()
            val password = binding.editLogipassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.registerUser(email, password)
            }
        }
        binding.checkboxRemember.setOnCheckedChangeListener { _, isChecked ->
            binding.signupregister.isEnabled = isChecked
        }



    }
    private fun observe() {
        viewModel.registerUi.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterViewModel.RegisterUiState.Loading -> {
                    binding.animationView22.visible()
                }

                is RegisterViewModel.RegisterUiState.Error -> {
                    binding.animationView22.gone()
                    Toast.makeText(requireContext(), it.errormessage, Toast.LENGTH_SHORT).show()
                }
                is RegisterViewModel.RegisterUiState.EmptyFields -> {
                    binding.animationView22.gone()
                    Toast.makeText(requireContext(), it.emptyMessage, Toast.LENGTH_SHORT).show()

                }

                is RegisterViewModel.RegisterUiState.Success -> {
                    binding.animationView22.gone()
                    if (binding.checkboxRemember.isChecked) {
                        val prefs = SharedPrefManager(requireContext())
                        prefs.saveIsLoggedIn(true)
                        prefs.saveEmail(it.email)
                        prefs.saveUser(it.email)

                    }
                    Toast.makeText(requireContext(),"Registered: ${it.email}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

                }

            }
        }
    }
}


