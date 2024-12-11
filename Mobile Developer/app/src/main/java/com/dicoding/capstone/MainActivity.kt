package com.dicoding.capstone

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mendapatkan NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_bottom_nav) as NavHostFragment

        // Mendapatkan NavController dari NavHostFragment
        navController = navHostFragment.navController

        // Mendapatkan BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        // Menyambungkan BottomNavigationView dengan NavController
        bottomNavigationView.setupWithNavController(navController)

        // Mengatur listener untuk perubahan destinasi (opsional)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_detail_article,
                    R.id.nav_mainFragment,
                R.id.nav_detail_stress -> {
                    bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }
}
