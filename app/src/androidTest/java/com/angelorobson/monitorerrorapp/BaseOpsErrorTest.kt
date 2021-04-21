package com.angelorobson.monitorerrorapp

import com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.viewmodel.OpsErrorDetailsViewModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import com.angelorobson.monitorerrorapp.utils.NavigationNavigator
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


@ExperimentalCoroutinesApi
open class BaseOpsErrorTest {

    @MockK
    var useCase = mockk<OpsErrorsUseCase>()

    @MockK
    var navigator = mockk<NavigationNavigator>()

    lateinit var module: Module

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        module = module(override = true) {
            viewModel {
                OpsErrorsViewModel(
                    useCase,
                    navigator
                )
            }
        }

        loadKoinModules(module)
    }

    @After
    fun cleanUp() {
        unloadKoinModules(module)
    }

}