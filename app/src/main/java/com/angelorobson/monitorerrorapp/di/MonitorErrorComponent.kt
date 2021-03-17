package com.angelorobson.monitorerrorapp.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent

@ExperimentalCoroutinesApi
object MonitorErrorComponent : KoinComponent {

    fun inject() = loadMonitorErrorModules()

}