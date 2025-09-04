package com.example.movamovieapp.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentLoginBinding
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        loginClick()
        binding.imageView8arrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.signuplogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }


    }

    private fun loginClick() {
        binding.signinbuttonlogin.setOnClickListener {


            val email = binding.editLoginemail.text.toString().trim()
            val password = binding.editLogipassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.animationView11.visible()
                viewModel.loginUser(email, password)


            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    private fun observeData() {
        viewModel.loginUi.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginUi.Loading -> {
                    lifecycleScope.launch {
                        delay(2000)
                        binding.animationView11.gone()

                    }

                }

                is LoginUi.Success -> {
                    if (binding.checkboxRemember.isChecked) {
                        val prefs = SharedPrefManager(requireContext())
                        prefs.saveIsLoggedIn(true)

                    }
                    binding.animationView11.gone()
                    Toast.makeText(requireContext(), state.successMessage, Toast.LENGTH_SHORT)
                        .show()
                    lifecycleScope.launch {
                        delay(2000)
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())


                    }

                }

                is LoginUi.Error -> {
                    binding.animationView11.gone()
                    Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }


            }
        }
    }
}

