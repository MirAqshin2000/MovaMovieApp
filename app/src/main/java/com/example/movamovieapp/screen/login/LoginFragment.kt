package com.example.movamovieapp.screen.login

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentLoginBinding
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    private var hasNavigated = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val isNightMode = (requireContext().resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        binding.imageView13.setImageResource(
            if (isNightMode) R.drawable.apple else R.drawable.appleblack
        )
        observeData()

        loginClick()

        binding.signuplogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }



    }
    fun Context.isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }



    private fun loginClick() {
        binding.signinbuttonlogin.setOnClickListener {
            if (!binding.signinbuttonlogin.isEnabled) return@setOnClickListener


            val email = binding.editLoginemail.text.toString().trim()
            val password = binding.editLogipassword.text.toString().trim()


            if (email.isEmpty() || password.isEmpty()) {
                binding.editLoginemail.error = "Email is required"
                binding.editLogipassword.error = "Password is required"
                shakeView(binding.editLoginemail)
                shakeView(binding.editLogipassword)
                binding.signinbuttonlogin.isEnabled = true

            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                shakeView(binding.editLoginemail)
                binding.signinbuttonlogin.isEnabled = false


            }else if (password.length < 6){
                Toast.makeText(requireContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                shakeView(binding.editLogipassword)
                binding.signinbuttonlogin.isEnabled = true

            }
            else {
                if (!requireContext().isInternetAvailable()) {
                    Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
                    binding.signinbuttonlogin.isEnabled = true
                    return@setOnClickListener
                }

                viewModel.loginUser(email, password)
            }

        }

    }

    private fun observeData() {
        viewModel.loginUi.observe(viewLifecycleOwner) { state ->
            binding.signinbuttonlogin.isEnabled = true
            when (state) {
                is LoginUi.Loading -> {
                    lifecycleScope.launch {
                        binding.animationView11.visible()
                        delay(1000)
                        binding.animationView11.gone()

                    }


                }


                is LoginUi.Success -> {

                    if (hasNavigated) return@observe

                    val prefs = SharedPrefManager(requireContext())

                    if (binding.checkboxRemember.isChecked) {
                        prefs.saveIsLoggedIn(true)

                    }
                    prefs.saveUserEmail(state.email)

                    binding.animationView11.gone()
                    lifecycleScope.launch {
                        binding.animationView11.visible()
                        delay(1500)
                        binding.animationView11.gone()
                        Toast.makeText(requireContext(), state.successMessage, Toast.LENGTH_SHORT)
                            .show()

                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                            hasNavigated = true
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
                        }

                    }


                }
                is LoginUi.Error -> {
                    binding.animationView11.gone()
                    Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialogView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)
                val alertDialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogView)
                    .create()

                val titleTextView = dialogView.findViewById<TextView>(R.id.tvTitle)
                val messageTextView = dialogView.findViewById<TextView>(R.id.tvMessage)
                val noButton = dialogView.findViewById<TextView>(R.id.btnNo)
                val yesButton = dialogView.findViewById<TextView>(R.id.btnYes)
                titleTextView.text = "Log Out"
                messageTextView.text = "Are you sure you want to log out?"
                noButton.setOnClickListener {
                    alertDialog.dismiss()
                }
                yesButton.setOnClickListener {
                    requireActivity().finishAffinity()
                }
                alertDialog.show()
            }


        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}

