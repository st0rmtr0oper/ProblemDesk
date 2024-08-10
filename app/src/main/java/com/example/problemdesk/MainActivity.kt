package com.example.problemdesk

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.problemdesk.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging


//TODO 1 - UI
//TODO - problem form design
//TODO - collections representation
//TODO 1.8.1 - popup messages (modal? idk), card handling

//TODO 1.8 - themes, custom styles, colors string resources
//TODO 1.9 - final design

//TODO 2 - MVVM for the rest

// какие юзкейсы? какой ответ с бэка?
//TODO 3 - final flow logic
//TODO cards transfer
//TODO caching? app should remember user login
//TODO need to add an exit button in profile?

//TODO 4 - backend, firebase, pushs, api, retrofit ...   +  manager UI, graphs
//manager ui


//TODO need to check all for following MVVM, Clean Arch and SOLID   ---!!!

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
//    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //navView and navController
        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.visibility = View.GONE

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.navigation_profile -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
//                R.id.navigation_statistics -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
//                else -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            }
//            supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_problem_form, R.id.navigation_my_problems, R.id.navigation_my_tasks, R.id.navigation_master, R.id.navigation_profile
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//

        NavigationUI.setupWithNavController(navView, navController)

//        NavigationUI.setupActionBarWithNavController(this, navController)
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)


        // Setup ActionBar with NavController and AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_login,
                R.id.navigation_master,
                R.id.navigation_statistics,
                R.id.navigation_my_problems,
                R.id.navigation_my_tasks,
                R.id.navigation_problem_form,
                R.id.navigation_profile
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Add a listener to control the Up button visibility, custom buttons, and title
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_profile -> {
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.profile_exit_menu)
                    supportActionBar?.title = getString(R.string.title_profile)
                }
                else -> {
                    toolbar.menu.clear()
                    supportActionBar?.title = destination.label
                }
            }
        }


        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        })


//        dI3wqql_Rhi5an3cdExtaL:APA91bHv_pr2paxL_1ocO40Xwh_PIQxmJW-jHCisZuFBwyLhscXS9e27s4Kw_wEkvPzuCywB7-Cq_9iZ_bLbvMJcxMRRk1Kw3QIkOHWb3tSuByhW_RkO3RKw9cQuwjpqoklisE4SKiqx
//        lifecycleScope.launch {
//            try {
//                val repository = DeskRepositoryImplementation()
//                val request = Request(1, Token)
//                repository.sendMessage(request)
//            } catch(_: Exception) {
//            }
//        }
    }

    // set up dynamic bottomNavBar
    fun setupBottomNavMenu(userRole: String) {
        val navView = binding.navView
        navView.menu.clear()
        when (userRole) {
            "master" -> {
                navView.inflateMenu(R.menu.bottom_nav_master)
            }

            "complainer" -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_common)
            }

            "executor" -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_common)
            }

            "manager" -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_manager)
            }
        }
        navView.visibility = View.VISIBLE
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Выход")
            setMessage("Вы хотите выйти из приложения?")
            setPositiveButton("Да") { _, _ ->
                finish()
            }
            setNegativeButton("Нет", null)
            show()
        }
    }





//    override fun onSupportNavigateUp(): Boolean {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
//        val navController = navHostFragment.navController
//        return navController.navigateUp()
//    }
}
