package com.example.movamovieapp.screen.subscribe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.CardAdapter
import com.example.movamovieapp.adapters.PaymentAdapter
import com.example.movamovieapp.databinding.FragmentPaymentBinding
import com.example.movamovieapp.model.CardModel
import com.example.movamovieapp.model.PaymentModel
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.maskCardNumberGrouped
import com.example.movamovieapp.util.visible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var cardadapter: CardAdapter
    private val viewModel: PaymentViewModel by viewModels()
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
        eachpayment.add(PaymentModel(R.drawable.google, "Google Pay", false))
        eachpayment.add(PaymentModel(R.drawable.apple, "Apple Pay", false))


        adapter.updateList(eachpayment)

        cardadapter = CardAdapter()

        binding.rv2.adapter = cardadapter
        observe()




        binding.button5addcard.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2000)
                binding.animationView22.gone()
                findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToNewCardFragment())


            }


        }

        var selectedPayment: PaymentModel? = null

        adapter.onSelectClickListener = { payment ->
            selectedPayment = payment
            cardadapter.clearSelection()
        }

        var selectedCard: CardModel? = null
        cardadapter.onSelectClickListener = { card ->
            selectedCard = card
            adapter.clearSelection()

        }

        binding.button4.setOnClickListener {
            lifecycleScope.launch {
                binding.animationView22.visible()
                delay(2000)
                binding.animationView22.gone()

                when {
                    selectedCard != null -> {
                        val maskedNumber = maskCardNumberGrouped(selectedCard!!.cardNumber)
                        val action =
                            PaymentFragmentDirections.actionPaymentFragmentToSummaryFragment(
                                cardNumber = maskedNumber,
                                cardImage = selectedCard!!.cardImage,
                            )
                        findNavController().navigate(action)
                    }

                    selectedPayment != null -> {
                        val action =
                            PaymentFragmentDirections.actionPaymentFragmentToSummaryFragment(
                                cardNumber = selectedPayment!!.cardNumber,
                                cardImage = selectedPayment!!.image,
                                paymentName = selectedPayment!!.title
                            )
                        findNavController().navigate(action)
                    }

                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "Card or Payment not selected!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }









        cardadapter.onItemClickListener = { cardId ->
            val dialogView =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)
            val alertDialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .create()

            val titleTextView = dialogView.findViewById<TextView>(R.id.tvTitle)
            val messageTextView = dialogView.findViewById<TextView>(R.id.tvMessage)
            val noButton = dialogView.findViewById<TextView>(R.id.btnNo)
            val yesButton = dialogView.findViewById<TextView>(R.id.btnYes)
            titleTextView.text = "Delete Card"
            messageTextView.text = "Are you sure you want to delete this Card?"
            noButton.setOnClickListener {
                alertDialog.dismiss()
            }
            yesButton.setOnClickListener {
                viewModel.deleteCard(cardId.id)
                alertDialog.dismiss()
            }
            alertDialog.show()


        }
    }


    private fun observe() {
        viewModel.cards.observe(viewLifecycleOwner) {
            Log.d("CARD_DEBUG", "List size: ${it.size}")

            cardadapter.updateList(it)

        }
        viewModel.getCards()

    }
}

