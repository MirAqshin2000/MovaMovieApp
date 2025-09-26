package com.example.movamovieapp.di

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApp :Application(){
    override fun onCreate() {
        super.onCreate()

        val prefs = getSharedPreferences("MyPrefs_${FirebaseAuth.getInstance().currentUser?.uid ?: "guest"}", MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}