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
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class OpsErrorFragmentTest : BaseOpsErrorTest() {


    private val list = listOf(
        OpsErrorModel(
            source = "souce",
            errorsCount = 5
        )
    )


    @Test
    fun test_show_all_widgets_onScreen() {
        coEvery { useCase.getOpsErrors(4) } returns flowOf(list)

        launchFragment()

        opsErrorRobot {
            visibleOpsErrorRecycler()
            notVisibleButtonTryAgain()
            visibleSourceErrorTextView()
            visibleArrowForwardImageView()
            visibleCountTextView()
        }
    }

    @Test
    fun test_show_button_try_again_when_throw_exceptions() {
        coEvery { useCase.getOpsErrors(4) } returns callbackFlow { throw Exception("error") }

        launchFragment()

        opsErrorRobot {
            visibleButtonTryAgain()
        }
    }

    private fun launchFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        navController.setGraph(R.navigation.my_nav)

        val args = Bundle().apply {
            putInt("hour", 4)
        }


        launchFragmentInContainer<OpsErrorFragment>(
            fragmentArgs = args, // Bundle
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
    }
}