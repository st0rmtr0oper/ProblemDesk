package com.example.problemdesk

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.problemdesk.unfiltered.DeskRepository
import com.example.problemdesk.databinding.ActivityMainBinding
import com.example.problemdesk.unfiltered.Request
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

//TODO 1 - UI
//TODO 1.1 - problem_form UI, spinners
//TODO 1.2 - my_problems UI, tabhost, recyclerViews, spinner click
//TODO 1.3 - my_tasks UI, tabhost, recyclerViews, spinner click
//TODO 1.4 - master UI, tabhost, recyclerViews, spinner click
//TODO 1.5 - profile UI, tabhost
//TODO 1.6 - manager UI
//TODO 1.7 - navigation UI, bottom nav bar design
//TODO 1.8 - themes
//TODO 1.9 - final design

//TODO 2 - MVVM

//TODO 3 - flow logic, mocking, navigation, dynamic bottom nav bar

//TODO 4 - backend, firebase, pushs, api, retrofit ...   +  manager UI, graphs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_problem_form, R.id.navigation_my_problems, R.id.navigation_my_tasks, R.id.navigation_master, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.isVisible = destination.id != R.id.navigation_login && destination.id != R.id.navigation_manager
        }

        var Token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task -> if (!task.isSuccessful) {
            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }
            // Get new FCM registration token
            val token = task.result
            Token = token
            Log.d("!!!---[FCM token]---!!!", token)
        })
//        e9foPUHNTlGrd89FjYdApU:APA91bGd8-fp0RYhVpSxgeJh4WVCAa0wAqMvs1xC_uRf1geAXU2Tj4ALjAC4DrkOyFfMnT8MNL1Sf9mwfrEDVM8hcJ2MC600FZn3e6mImVkCojxoFdOihixMpIYk3J7rOtEEJBV8W2Uu

        lifecycleScope.launch {
            try {
                val repository = DeskRepository()
                val request = Request(1, Token)
                repository.sendMessage(request)
            } catch(_: Exception) {
            }
        }
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp()
    }
}