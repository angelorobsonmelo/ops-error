package com.angelorobson.monitorerrorapp.di

import com.angelorobson.monitorerrorapp.converters.OpsErrorConverter
import com.angelorobson.monitorerrorapp.converters.OpsErrorDetailsConverter
import com.angelorobson.monitorerrorapp.repository.OpsErrorRepository
import com.angelorobson.monitorerrorapp.repository.remote.OpsErrorService
import com.angelorobson.monitorerrorapp.ui.viewmodels.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
private val viewModelModules = module(override = true) {
    viewModel { OpsErrorsViewModel(get()) }
}

@ExperimentalCoroutinesApi
private val useCaseModules = module(override = true) {
    single { OpsErrorsUseCase(get(), get(), get()) }
}

private val repositoryModules = module(override = true) {
    single { OpsErrorRepository(get()) }
}

private val serviceModules = module(override = true) {
    single { OpsErrorService().api() }
}

private val converterModules = module(override = true) {
    single { OpsErrorConverter() }
    single { OpsErrorDetailsConverter() }
}

@ExperimentalCoroutinesApi
internal val monitorErrorModules = viewModelModules +
        useCaseModules +
        repositoryModules +
        serviceModules +
        converterModules