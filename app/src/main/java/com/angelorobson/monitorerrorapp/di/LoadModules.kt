package com.angelorobson.monitorerrorapp.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.context.loadKoinModules

@ExperimentalCoroutinesApi
private val lazyLoadMonitorErrorModules by lazy { loadKoinModules(monitorErrorModules) }

@ExperimentalCoroutinesApi
internal fun loadMonitorErrorModules() = lazyLoadMonitorErrorModules