package com.example.movamovieapp.screen.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.movamovieapp.R
import com.example.movamovieapp.adapters.IntroPagerAdapter
import com.example.movamovieapp.databinding.FragmentOnboardingBinding
import com.example.movamovieapp.model.IntroModel
import com.example.movamovieapp.util.SharedPrefManager
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    private val adapter = IntroPagerAdapter()

    private var isNavigating = false



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


        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == adapter.itemCount - 1) {
                    binding.getstardetbutton.visible()
                } else {
                    binding.getstardetbutton.gone()
                }
            }
        })




        binding.getstardetbutton.setOnClickListener {
            if (isNavigating) {
                return@setOnClickListener
            }
            isNavigating = true


            lifecycleScope.launch {
                if (!isAdded) return@launch
                binding.animationView22.visible()
                delay(1500)
                if (!isAdded) return@launch
                binding.animationView22.gone()
                if (!isAdded) {
                    isNavigating = false
                    return@launch
                }


                val currentItem = binding.viewPager2.currentItem
                val lastItem = adapter.itemCount - 1
                if (currentItem < lastItem) {
                    binding.viewPager2.setCurrentItem(currentItem + 1, true)
                    isNavigating = false
                } else {
                    val navController = findNavController()
                    val actionId = OnboardingFragmentDirections.actionOnboardingFragmentToLetsFragment()
                    if (navController.currentDestination?.getAction(R.id.action_onboardingFragment_to_letsFragment) != null){
                        val prefs = SharedPrefManager(requireContext())
                        prefs.setFirstLaunch(false)
                        navController.navigate(actionId)

                    }
                    isNavigating = false

                }

            }


        }
    }
}