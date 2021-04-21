package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.monitorerrorapp.BaseOpsErrorTest
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.opsErrorRobot
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.viewmodel.OpsErrorDetailsViewModel
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
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class OpsErrorDetailsFragmentTest {

    @MockK
    lateinit var useCase: OpsErrorsUseCase


    lateinit var module: Module

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        module = module(override = true) {
            viewModel {
                OpsErrorDetailsViewModel(useCase)
            }
        }

        loadKoinModules(module)
    }

    @After
    fun cleanUp() {
        unloadKoinModules(module)
    }


    private val list = listOf(
        OpsErrorDetailsModel(
            name = "exception",
            date = Date()
        )
    )


    @Test
    fun test_show_all_widgets_onScreen() {
        coEvery { useCase.getOpsErrorDetails("source", 4) } returns flowOf(list)

        launchFragment()

        opsErrorDetailsRobot {
            visibleOpsErrorDetailsRecycler()
            visibleDateTextView()
            visibleSourceErrorTextView()
            notVisibleButtonTryAgain()
        }
    }

    @Test
    fun test_show_button_try_again_when_throw_exceptions() {
        coEvery {
            useCase.getOpsErrorDetails(
                "source",
                4
            )
        } returns callbackFlow { throw Exception("error") }

        launchFragment()

        opsErrorDetailsRobot {
            visibleButtonTryAgain()
        }
    }

    private fun launchFragment() {
        val args = Bundle().apply {
            putInt("hour", 4)
            putString("source", "souce")
            putString("title", "souce")
        }

        launchFragmentInContainer<OpsErrorDetailsFragment>(
            fragmentArgs = args, // Bundle
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )
    }
}