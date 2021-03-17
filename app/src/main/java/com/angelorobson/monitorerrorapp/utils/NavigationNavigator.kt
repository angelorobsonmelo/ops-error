package com.angelorobson.monitorerrorapp.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

class NavigationNavigator(private val viewId: Int, private val activityService: ActivityService) {

    private val navController: NavController?
        get() = activityService.activity?.findNavController(viewId)

    fun to(@IdRes resId: Int, args: Bundle? = null) {
        navController?.navigate(resId, args)
    }

    fun to(directions: NavDirections) {
        navController?.navigate(directions)
    }

    fun popBackStack() {
        navController?.popBackStack()
    }

}