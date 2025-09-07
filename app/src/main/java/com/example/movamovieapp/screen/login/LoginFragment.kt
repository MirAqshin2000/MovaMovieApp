package com.example.movamovieapp.screen.login

import android.os.Bundle
import android.view.View
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


            if (email.isEmpty() || password.isEmpty()) {
                binding.editLoginemail.error = "Email is required"
                binding.editLogipassword.error = "Password is required"
                shakeView(binding.editLoginemail)
                shakeView(binding.editLogipassword)
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                shakeView(binding.editLoginemail)

            }else if (password.length < 6){
                Toast.makeText(requireContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                shakeView(binding.editLogipassword)
            }
            else {
                viewModel.loginUser(email, password)
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
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), state.successMessage, Toast.LENGTH_SHORT)
                            .show()

                        delay(2000)
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())


                    }


                }

                is LoginUi.Error -> {
                    binding.animationView11.gone()
                    Toast.makeText(requireContext(), "Email and password are incorrect" , Toast.LENGTH_SHORT).show()
                    shakeView(binding.editLoginemail)
                    shakeView(binding.editLogipassword)
                }


            }
        }
    }

    private fun shakeView(view: View) {
        val shake =
            android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        view.startAnimation(shake)
    }
}

