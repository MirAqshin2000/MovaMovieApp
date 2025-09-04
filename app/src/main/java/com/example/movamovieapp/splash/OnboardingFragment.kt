package com.example.movamovieapp.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.adapters.IntroPagerAdapter
import com.example.movamovieapp.databinding.FragmentOnboardingBinding
import com.example.movamovieapp.model.IntroModel
import com.example.movamovieapp.util.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    val adapter = IntroPagerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewPager2.adapter = adapter

        binding.getstardetbutton.setOnClickListener {

            val prefs = SharedPrefManager(requireContext())
            prefs.setFirstLaunch(false)

            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToRegisterFragment())
        }

        binding.dotindicator.attachTo(binding.viewPager2)
        val list = listOf(

            IntroModel(
                "Welcome to Mova",
                "The best movie streaming app of the century to make your days great!"

            ),
            IntroModel(
                "Discover Thousands of Movies",
                "From timeless classics to the latest blockbusters — everything is just a tap away!"
            ),
            IntroModel(
                "Watch Anytime, Anywhere",
                "Enjoy seamless streaming on your phone, tablet, or TV entertainment in your pocket."
            ),
        )
        adapter.updateList(list)

    }
}