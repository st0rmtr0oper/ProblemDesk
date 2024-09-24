package com.example.problemdesk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserRole
import com.example.problemdesk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

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
        val userId = getSharedPrefsUserRole(application)
        if (userId!=0) {
            setUpBottomNavMenu(userId)
        }
        setUpExit()
    }

    private fun setUpBottomNavigation() {
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

                R.id.navigation_rating -> {
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.profile_exit_menu)
                    supportActionBar?.title = getString(R.string.title_rating)
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
        Log.i("userrole", userRole.toString())
        when (userRole) {
            1 -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_common)
            }

            2 -> {
                navView.inflateMenu(R.menu.bottom_nav_master)
            }

            3 -> {
                navView.inflateMenu(R.menu.bottom_nav_menu_manager)
            }
        }
        navView.visibility = View.VISIBLE
    }

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





//TODO что это за хуйня
//2024-09-24 20:53:50.104  3008-4267  System                  com.example.problemdesk              W  ClassLoader referenced unknown path: system/framework/mediatek-cta.jar
//2024-09-24 20:53:50.107  3008-4267  System.out              com.example.problemdesk              I  [socket] e:java.lang.ClassNotFoundException: com.mediatek.cta.CtaUtils
//2024-09-24 20:53:50.468  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  <-- 401 Unauthorized https://timofmax1.fvds.ru/refresh-user-token (559ms)
//2024-09-24 20:53:50.468  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  Server: nginx/1.27.1
//2024-09-24 20:53:50.469  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  Date: Tue, 24 Sep 2024 13:54:59 GMT
//2024-09-24 20:53:50.469  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  Content-Type: application/json
//2024-09-24 20:53:50.469  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  Content-Length: 30
//2024-09-24 20:53:50.470  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  Connection: keep-alive
//2024-09-24 20:53:50.470  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  www-authenticate: Bearer
//2024-09-24 20:53:50.471  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  {"detail":"Not authenticated"}
//2024-09-24 20:53:50.472  3008-4267  okhttp.OkHttpClient     com.example.problemdesk              I  <-- END HTTP (30-byte body)
//2024-09-24 20:53:50.488  3008-4236  AndroidRuntime          com.example.problemdesk              E  FATAL EXCEPTION: DefaultDispatcher-worker-1
//Process: com.example.problemdesk, PID: 3008
//retrofit2.HttpException: HTTP 401 Unauthorized
//at retrofit2.KotlinExtensions$await$2$2.onResponse(KotlinExtensions.kt:53)
//at retrofit2.OkHttpCall$1.onResponse(OkHttpCall.java:161)
//at okhttp3.internal.connection.RealCall$AsyncCall.run(RealCall.kt:519)
//at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
//at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
//at java.lang.Thread.run(Thread.java:919)
//Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [StandaloneCoroutine{Cancelling}@c7b92bc, Dispatchers.IO]