package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentNewCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewCardFragment :BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {
    private val viewModel: PaymentViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.card2geri.setOnClickListener {
            findNavController().popBackStack()
        }



        binding.kartnumarasi.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var cursorPosition = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                cursorPosition = binding.kartnumarasi.selectionStart.coerceAtLeast(0)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                s ?: run { isFormatting = false; return }

                val digits = s.toString().replace(" ", "")
                val formatted = StringBuilder()

                for (i in digits.indices) {
                    formatted.append(digits[i])
                    if ((i + 1) % 4 == 0 && i + 1 != digits.length) formatted.append(" ")
                }

                val newText = formatted.toString()
                binding.kartnumarasi.setText(newText)

                val cursorOffset = newText.length - s.toString().length
                val safeCursor = (cursorPosition + cursorOffset).coerceIn(0, newText.length)
                binding.kartnumarasi.setSelection(safeCursor)

                isFormatting = false
            }
        })




        binding.expiry.addTextChangedListener(object : TextWatcher {
            private var isFormatting: Boolean = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                val digits = s.toString().replace("/", "")
                val formatted = StringBuilder()

                for (i in digits.indices) {
                    formatted.append(digits[i])
                    if ((i + 1) % 2 == 0 && i + 1 != digits.length) {
                        formatted.append("/")
                    }
                }

                binding.expiry.setText(formatted.toString())
                binding.expiry.setSelection(formatted.length)

                isFormatting = false
            }
        })

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