package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentSummaryBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding

    private val args: SummaryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView59card.text = when {
            args.cardNumber.isNotEmpty() -> args.cardNumber
            args.paymentName.isNotEmpty() -> args.paymentName
            else -> ""
        }

        if (args.paymentName == "Apple Pay") {
            val isNightMode = (requireContext().resources.configuration.uiMode
                    and android.content.res.Configuration.UI_MODE_NIGHT_MASK) ==
                    android.content.res.Configuration.UI_MODE_NIGHT_YES

            if (isNightMode) {
                binding.imageView55.setImageResource(R.drawable.apple)
            } else {
                binding.imageView55.setImageResource(R.drawable.appleblack)
            }
        } else {
            binding.imageView55.setImageResource(args.cardImage.toInt())
        }

        binding.imageView54.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textView60.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.button5confirmpayment.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2000)
                binding.animationView22.gone()
                binding.cardsummary.visible()

            }
        }
        binding.buttonoksummary.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2500)
                binding.animationView22.gone()
                binding.cardsummary.gone()
                findNavController().navigate(SummaryFragmentDirections.actionSummaryFragmentToProfileFragment())

            }
        }
    }

}