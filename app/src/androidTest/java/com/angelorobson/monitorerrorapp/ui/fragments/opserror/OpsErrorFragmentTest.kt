package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.monitorerrorapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class OpsErrorFragmentTest {

    @Before
    fun setUp() {
        val args = Bundle().apply {
            putInt("hour", 4)
        }

        // detailed version
        launchFragmentInContainer<OpsErrorFragment>(
            fragmentArgs = args, // Bundle
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
    }

    @Test
    fun testNavigationToInGameScreen() {
        // Create a TestNavHostController
        onView(withId(R.id.ops_error_linearLayout)).check(matches(isDisplayed()))
    }
}