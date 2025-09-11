package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentSummaryBinding


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


        binding.textView59card.text = args.cardNumber
        binding.textView52.text = args.paymentName
        binding.imageView55.setImageResource(args.cardImage.toInt())


        binding.imageView54.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textView60.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}