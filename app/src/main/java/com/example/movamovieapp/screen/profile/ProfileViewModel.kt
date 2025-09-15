package com.example.movamovieapp.screen.profile

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
):ViewModel() {
    private val isdarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = isdarkMode


    fun setDarkMode(isDark: Boolean) {
        isdarkMode.value = isDark

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
    }
}




