package com.example.movamovieapp.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentFaqBinding


class FaqFragment : BaseFragment<FragmentFaqBinding>(FragmentFaqBinding::inflate) {
 val faqAdapter=FaqAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding.rvFaq.adapter=faqAdapter


        faqAdapter.updateList(
            listOf(
                Faq("What is Mova?", getString(R.string.what_is_Mova)),
                Faq("How do I add content to the list?",getString(R.string.how_list)),
                Faq("How to remove wishlist?",getString(R.string.how_to_remove_wishlist)),
                Faq("How do I subscribe to premium?",getString(R.string.how_premium)),
                Faq("How do I can download movies?",getString(R.string.how_download)),
                Faq("How to unsubscribe from premium?",getString(R.string.how_unsubrice))

        )
        )



    }

}