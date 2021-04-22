package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.monitorerrorapp.MonitorErrorBaseErrorTest
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import com.angelorobson.monitorerrorapp.utils.NavigationNavigator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class OpsErrorFragmentTest: MonitorErrorBaseErrorTest()  {


    private val list = listOf(
        OpsErrorModel(
            source = "souce",
            errorsCount = 5
        )
    )

    @Test
    fun test_show_all_widgets_onScreen() {
        // ARRANGE
        coEvery { useCase.getOpsErrors(4) } returns flowOf(list)

        // ACT
        launchFragment()

        // ASSERT
        opsErrorRobot {
            visibleOpsErrorTotalTextView()
            visibleOpsErrorRecycler()
            notVisibleButtonTryAgain()
            visibleSourceErrorTextView()
            visibleArrowForwardImageView()
            visibleCountTextView()
        }
    }

    @Test
    fun test_show_button_try_again_when_throw_exceptions() {
        // ARRANGE
        coEvery { useCase.getOpsErrors(4) } returns callbackFlow { throw Exception("error") }

        // ACT
        launchFragment()

        // ASSERT
        opsErrorRobot {
            visibleButtonTryAgain()
        }
    }

    private fun launchFragment() {
        val args = Bundle().apply {
            putInt("hour", 4)
        }

        launchFragmentInContainer<OpsErrorFragment>(
            fragmentArgs = args, // Bundle
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
    }
}