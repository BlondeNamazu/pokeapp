package com.example.pokeapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.pokeapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = requireNotNull(
            supportFragmentManager.findFragmentById(R.id.main_fragment_container)
                ?.findNavController()
        )
        val navGraph = navController.navInflater.inflate(R.navigation.poke_app)
        navController.graph = navGraph.apply {
            setStartDestination(R.id.home_fragment)
        }
    }
}
