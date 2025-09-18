package com.example.movamovieapp.screen.help

import android.os.Bundle
import android.view.View
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentContactBinding

class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate) {
var contactAdapter=ContactAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.rvContact.adapter=contactAdapter

        contactAdapter.updateList(
            listOf(
                Contact("Customer Service", R.drawable.headphone),
                Contact("Whatsapp",R.drawable.whatsapp),
                Contact("Website",R.drawable.web),
                Contact("Facebook",R.drawable.facebook),
                Contact("Instagram",R.drawable.instagram),
                Contact("Twitter",R.drawable.twitter),
            )

        )



    }

}