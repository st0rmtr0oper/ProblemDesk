package com.example.problemdesk

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.problemdesk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
//    private lateinit var navView: BottomNavigationView

    override fun onResume() {
        super.onResume()
        setUpBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO splash inst working on old versions
        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigation()
//        setUpActionBar()
        setUpExit()
    }

    private fun setUpBottomNavigation() {

        //TODO toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        NavigationUI.setupWithNavController(navView, navController)
        navView.visibility = View.GONE

        // Setup ActionBar with NavController and AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_login,
                R.id.navigation_master,
                R.id.navigation_charts,
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
                    binding.navView.visibility = View.VISIBLE // Show Bottom Navigation Bar
                }

                R.id.navigation_login -> {
                    toolbar.menu.clear()
                    binding.navView.visibility = View.GONE // Hide Bottom Navigation Bar
                    supportActionBar?.title = getString(R.string.title_login) // Set title for Login
                }

                else -> {
                    toolbar.menu.clear()
                    supportActionBar?.title = destination.label
                    binding.navView.visibility =
                        View.VISIBLE // Show Bottom Navigation Bar for other destinations
                }
            }
        }
    }

    // set up dynamic bottomNavBar by role
    fun setUpBottomNavMenu(userRole: Int) {
        val navView = binding.navView
        navView.menu.clear()
        when (userRole) {
            1 -> {
                navView.inflateMenu(R.menu.bottom_nav_master)
            }

            2 -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_common)
            }

            3 -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_manager)
            }
        }
        navView.visibility = View.VISIBLE
    }

//    private fun setUpActionBar() {
//        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//    }

    private fun setUpExit() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        })
    }

//    https://stackoverflow.com/questions/65182773/what-does-androidconfigchanges-do

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
}


//TODO onDestroy?

//TODO wat is sat (in onCreate)

//        enableEdgeToEdge()

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


//----------


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
