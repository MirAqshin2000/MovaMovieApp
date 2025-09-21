package com.example.movamovieapp.screen.login

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentRegisterBinding
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    private val viewModel: RegisterViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isNightMode = (requireContext().resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        binding.imageViewApple.setImageResource(
            if (isNightMode) R.drawable.apple else R.drawable.appleblack
        )


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
                binding.editLoginemail.error = "Email is required"
                binding.editLogipassword.error = "Password is required"
                shakeView(binding.editLoginemail)
                shakeView(binding.editLogipassword)
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                shakeView(binding.editLoginemail)
            } else if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    "Password must be at least 6 characters",
                    Toast.LENGTH_SHORT
                ).show()
                shakeView(binding.editLogipassword)
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
                    Toast.makeText(requireContext(), "Registered Successfully: ${it.email}", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())

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


