package ru.investlifestyle.app

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.paging.ExperimentalPagingApi
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import ru.investlifestyle.app.databinding.ActivityMainBinding
import ru.investlifestyle.app.di.DaggerAppComponent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    @ExperimentalPagingApi
    private val component by lazy {
        (application as App).daggerAppComponent
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications)
            .setOpenableLayout(binding.mainDrawerLayout)
            .build()
        setSupportActionBar(binding.myToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupNavController()
    }

    private fun setupNavController() {
        binding.mainNavigationDrawerView.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}