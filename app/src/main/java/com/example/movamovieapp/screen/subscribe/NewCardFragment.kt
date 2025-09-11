package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentNewCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCardFragment : Fragment() {
    private lateinit var binding: FragmentNewCardBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewCardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.card2geri.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonaddcard.setOnClickListener {
            val name = binding.kartadi.text.toString()
            val number = binding.kartnumarasi.text.toString()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                viewModel.addCard(name, number)
                findNavController().popBackStack()
            }
        }
    }
}