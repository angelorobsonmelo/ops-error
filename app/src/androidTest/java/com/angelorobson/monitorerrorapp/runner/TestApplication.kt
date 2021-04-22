package com.angelorobson.monitorerrorapp.runner

import android.app.Application
import com.angelorobson.monitorerrorapp.di.applicationModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@OptIn(ExperimentalCoroutinesApi::class)
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TestApplication)
        }
    }

}
