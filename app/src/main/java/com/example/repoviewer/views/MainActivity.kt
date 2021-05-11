package com.example.repoviewer.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.repoviewer.R
import com.example.repoviewer.databinding.ActivityMainBinding
import com.example.repoviewer.utils.TAG
import com.example.repoviewer.utils.setupWithNavController
import com.example.repoviewer.viewmodel.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity()/*, BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener*/ {
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavView: BottomNavigationView
    lateinit var navController: NavController
    private var currentNavController: LiveData<NavController>? = null


    private val sharedViewModel: SharedViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupViews() {
        var navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostContainer.id) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavView = findViewById(R.id.bottom_nav)
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
//        var appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)----> will give back button
        var appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_home,
                R.id.menu_detail,
                R.id.menu_comment,
                R.id.repoDetails
            )
        )//--> will not give back button
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }


    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        Log.e(TAG, "setupBottomNavigationBar: " + R.id.menu_home + " " + R.navigation.home)
        Log.e(TAG, "setupBottomNavigationBar: " + R.id.menu_detail + " " + R.navigation.detail)
        Log.e(TAG, "setupBottomNavigationBar: " + R.id.menu_comment + " " + R.navigation.comment)
        val navGraphIds = listOf(R.navigation.home, R.navigation.detail, R.navigation.comment)
        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        if (controller.value != null) {
            Log.e(TAG, "setupBottomNavigationBar: not null ")

        } else {
            Log.e(TAG, "setupBottomNavigationBar:  null ")

        }
        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            Log.e(TAG, "setupBottomNavigationBar: observe ")
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}