package com.example.movamovieapp.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentHelpBinding
import com.google.android.material.tabs.TabLayoutMediator


class HelpFragment : BaseFragment<FragmentHelpBinding>(FragmentHelpBinding::inflate) {

    private val pagerAdapter by lazy { HelpPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.helpPager.adapter = pagerAdapter

setTablayout()
        popClick()
    }

    private fun setTablayout() {

        TabLayoutMediator(binding.tabLayout2, binding.helpPager) { tab, position ->
            when (position) {
                0 -> tab.text = "FAQ"
                1 -> tab.text = "Contact us"
            }
        }.attach()
    }

    private fun popClick(){
        binding.imageView58.setOnClickListener {
         findNavController().popBackStack()
        }

    }
}