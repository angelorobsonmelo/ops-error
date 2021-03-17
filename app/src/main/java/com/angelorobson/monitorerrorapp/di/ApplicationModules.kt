package com.angelorobson.monitorerrorapp.di

import android.text.format.DateUtils
import com.angelorobson.monitorerrorapp.BuildConfig.BASE_URL_API
import com.angelorobson.monitorerrorapp.utils.BaseWebService
import org.koin.dsl.module

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
}