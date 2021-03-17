package com.angelorobson.monitorerrorapp.models

import com.squareup.moshi.JsonClass


data class OpsErrorModel(
    val noErrors: Int,
    val source: String
)