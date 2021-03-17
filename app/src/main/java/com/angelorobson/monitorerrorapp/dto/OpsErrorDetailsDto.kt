package com.angelorobson.monitorerrorapp.dto

import com.squareup.moshi.JsonClass
import java.util.Date


@JsonClass(generateAdapter = true)
data class OpsErrorDetailsDto(
    val date: Date,
    val name: String
)