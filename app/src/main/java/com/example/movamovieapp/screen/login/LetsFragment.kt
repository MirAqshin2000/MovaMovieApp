package com.example.movamovieapp.screen.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentLetsBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LetsFragment : BaseFragment<FragmentLetsBinding>(FragmentLetsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView25.visible()
                delay(2000)
                binding.animationView25.gone()
                findNavController().navigate(LetsFragmentDirections.actionLetsFragmentToLoginFragment())

            }

        }
        binding.textView3signup.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView25.visible()
                delay(2000)
                binding.animationView25.gone()
                findNavController().navigate(LetsFragmentDirections.actionLetsFragmentToRegisterFragment())

            }

        }


    }



}