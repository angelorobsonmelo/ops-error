package com.angelorobson.monitorerrorapp.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OpsErrorDetailsDto(
    val date: String,
    val name: String
)