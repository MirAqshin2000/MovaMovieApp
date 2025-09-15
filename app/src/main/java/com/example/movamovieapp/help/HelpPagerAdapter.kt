package com.example.movamovieapp.help

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movamovieapp.help.FaqFragment

class HelpPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {

            0 -> FaqFragment()
            1 -> ContactFragment()
            else -> throw Resources.NotFoundException("Position Not Found")

        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}