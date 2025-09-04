package com.example.movamovieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movamovieapp.databinding.ActivityMainBinding
import com.example.movamovieapp.util.gone
import com.example.movamovieapp.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                R.id.detailFragment -> bottomMenu.visible()
                R.id.profileFragment -> bottomMenu.visible()
                else -> bottomMenu.gone()
            }
        }

    }
}