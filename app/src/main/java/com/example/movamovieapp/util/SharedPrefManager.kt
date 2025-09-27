package com.example.movamovieapp.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SharedPrefManager@Inject constructor
    (context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)


    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean("is_first_launch", true)
    }

    fun setFirstLaunch(isFirst: Boolean) {
        sharedPreferences.edit().putBoolean("is_first_launch", isFirst).apply()
    }

    fun saveIsLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun saveIsFirstLaunch(isFirst: Boolean) {
        sharedPreferences.edit().putBoolean("is_first_launch", isFirst).apply()
    }
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString("email", email).apply()

    }
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }
    fun logoutButKeepData() {
        saveIsLoggedIn(false)
    }

    fun addToken(token: String) {
        sharedPreferences.edit { putString("token", token) }
    }
    fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString("user_email", email).apply()
    }
    fun getUserEmail(): String? {
        return sharedPreferences.getString("user_email", "")
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }
    }
