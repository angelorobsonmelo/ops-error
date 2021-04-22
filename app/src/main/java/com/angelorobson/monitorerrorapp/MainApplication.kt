package com.angelorobson.monitorerrorapp

import android.app.Application
import com.angelorobson.monitorerrorapp.di.applicationModules
import com.angelorobson.monitorerrorapp.di.loadMonitorErrorModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application() {


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(applicationModules)
            loadMonitorErrorModules()
        }
    }
}