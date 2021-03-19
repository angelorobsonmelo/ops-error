package com.angelorobson.monitorerrorapp.di

import com.angelorobson.monitorerrorapp.converters.OpsErrorConverter
import com.angelorobson.monitorerrorapp.converters.OpsErrorDetailsConverter
import com.angelorobson.monitorerrorapp.repository.OpsErrorRepository
import com.angelorobson.monitorerrorapp.repository.remote.OpsErrorService
import com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.viewmodel.OpsErrorDetailsViewModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val converterModules = module(override = true) {
    single { OpsErrorConverter() }
    single { OpsErrorDetailsConverter() }
}

private val repositoryModules = module(override = true) {
    single { OpsErrorRepository(get()) }
}

private val serviceModules = module(override = true) {
    single { OpsErrorService().api() }
}

@ExperimentalCoroutinesApi
private val useCaseModules = module(override = true) {
    factory { OpsErrorsUseCase(get(), get(), get()) }
}

@ExperimentalCoroutinesApi
private val viewModelModules = module(override = true) {
    viewModel { OpsErrorsViewModel(get(), get()) }
    viewModel { OpsErrorDetailsViewModel(get()) }
}

@ExperimentalCoroutinesApi
internal val monitorErrorModules = serviceModules +
        converterModules +
        repositoryModules +
        useCaseModules +
        viewModelModules