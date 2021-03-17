package com.angelorobson.monitorerrorapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.utils.ActivityService
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private val activityService: ActivityService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityService.onCreate(this)

        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.opsErrorFragment,
                R.id.opsErrorDetailsFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        activityService.onDestroy(this)
        super.onDestroy()
    }
}