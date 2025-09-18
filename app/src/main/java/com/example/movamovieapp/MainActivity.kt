package com.example.movamovieapp

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movamovieapp.databinding.ActivityMainBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val langCode = prefs.getString("app_language", "en") // default EN
        val context = com.example.movamovieapp.util.LocalHelper.setLocale(newBase, langCode ?: "en")
        super.attachBaseContext(context)      //dil deyismek
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as? NavHostFragment
        val navController = navHostFragment?.navController
        val bottomMenu = binding.bottomNavigationView
        if (navController != null) {
            bottomMenu.setupWithNavController(navController)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> bottomMenu.visible()
                R.id.exploreFragment -> bottomMenu.visible()
                R.id.profileFragment -> bottomMenu.visible()
                R.id.myListFragment2 -> bottomMenu.visible()
                R.id.downloadFragment2 -> bottomMenu.visible()
                else -> bottomMenu.gone()
            }
        }









    }

}