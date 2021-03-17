package com.angelorobson.monitorerrorapp.di

import android.text.format.DateUtils
import com.angelorobson.monitorerrorapp.BuildConfig.BASE_URL_API
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.utils.ActivityService
import com.angelorobson.monitorerrorapp.utils.BaseWebService
import com.angelorobson.monitorerrorapp.utils.NavigationNavigator
import com.angelorobson.monitorerrorapp.utils.customDateAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private const val HALF = 2

val applicationModules = module(override = true) {
    single {
        BaseWebService(
            baseUrl = BASE_URL_API,
            connectTimeout = DateUtils.MINUTE_IN_MILLIS / HALF,
            readTimeout = DateUtils.MINUTE_IN_MILLIS / HALF,
            writeTimeout = DateUtils.MINUTE_IN_MILLIS / HALF,
        )
    }

    single {
        NavigationNavigator(R.id.navHostFragment, get())
    }

    single {
        ActivityService()
    }

    single {
        val baseWebService = get<BaseWebService>()

        OkHttpClient.Builder()
            .connectTimeout(baseWebService.connectTimeout, TimeUnit.MILLISECONDS)
            .readTimeout(baseWebService.readTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(baseWebService.writeTimeout, TimeUnit.MILLISECONDS).build()
    }

    single {
        Moshi.Builder().add(customDateAdapter).build()
    }

}