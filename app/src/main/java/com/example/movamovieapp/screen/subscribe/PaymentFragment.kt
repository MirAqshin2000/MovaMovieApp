package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.PaymentAdapter
import com.example.movamovieapp.databinding.FragmentPaymentBinding
import com.example.movamovieapp.model.PaymentModel


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    val args: PaymentFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PaymentAdapter()

        binding.recyclerViewpayment.adapter = adapter


        binding.imageView53.setOnClickListener {
            findNavController().popBackStack()
        }

        val eachpayment = arrayListOf<PaymentModel>()
        eachpayment.add(PaymentModel(R.drawable.paypallogo, "PayPal", false))
        eachpayment.add(PaymentModel(R.drawable.google, "Google Pay", true))
        eachpayment.add(PaymentModel(R.drawable.apple, "Apple Pay", false))
        try {
            eachpayment.add(PaymentModel(R.drawable.mastercard, args.paymentargs, false))


        }catch (
e:Exception){

        }
        adapter.updateList(eachpayment)

    }
}