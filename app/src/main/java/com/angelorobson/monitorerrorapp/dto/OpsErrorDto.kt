package com.angelorobson.monitorerrorapp.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OpsErrorDto(
    val noErrors: Int,
    val source: String
)