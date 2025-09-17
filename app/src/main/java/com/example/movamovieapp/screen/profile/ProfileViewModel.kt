package com.example.movamovieapp.screen.profile

import android.content.SharedPreferences
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


    fun setDarkMode(isDark: Boolean, sharedPreferences: SharedPreferences) {
        isdarkMode.value = isDark
        sharedPreferences.edit().putBoolean("dark_mode", isDark).apply()


        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}




