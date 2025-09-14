@file:Suppress("DEPRECATION")

package com.example.movamovieapp.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocalHelper {
        fun setLocale(context: Context, languageCode: String): Context {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)

            return context.createConfigurationContext(config)
        }
    }
