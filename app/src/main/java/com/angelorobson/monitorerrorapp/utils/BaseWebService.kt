package com.angelorobson.monitorerrorapp.utils

data class BaseWebService(
    val baseUrl: String,
    val connectTimeout: Long,
    val readTimeout: Long,
    val writeTimeout: Long,
)