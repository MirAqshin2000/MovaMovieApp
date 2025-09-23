package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentSubscribeBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SubscribeFragment : Fragment() {
    private lateinit var binding: FragmentSubscribeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubscribeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView33.setOnClickListener {
            findNavController().popBackStack()

        }
        binding.materialCardView6.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(1500)
                binding.animationView22.gone()
                findNavController().navigate(
                    SubscribeFragmentDirections.actionSubscribeFragmentToPaymentFragment(
                        toString()
                    )
                )
            }
        }

            binding.materialcardview7.setOnClickListener {
                lifecycleScope.launch {
                    binding.animationView22.visible()
                    delay(1500)
                    binding.animationView22.gone()
                    findNavController().navigate(R.id.action_subscribeFragment_to_paymentFragment)
                }
            }


        }

}