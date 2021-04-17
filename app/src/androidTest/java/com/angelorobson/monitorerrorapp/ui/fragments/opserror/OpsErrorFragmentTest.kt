package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.monitorerrorapp.BaseOpsErrorTest
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class OpsErrorFragmentTest: BaseOpsErrorTest() {


    val list = listOf(
        OpsErrorModel(
            source = "souce",
            errorsCount = 5
        )
    )

    @Test
    fun testNavigationToInGameScreen() {
        val args = Bundle().apply {
            putInt("hour", 4)
        }

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.my_nav)


        coEvery { useCase.getOpsErrors(4) } returns flowOf(list)


        // detailed version
        launchFragmentInContainer<OpsErrorFragment>(
            fragmentArgs = args, // Bundle
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
        // Create a TestNavHostController
//        onView(withId(R.id.ops_error_linearLayout)).check(matches(isDisplayed()))
    }
}