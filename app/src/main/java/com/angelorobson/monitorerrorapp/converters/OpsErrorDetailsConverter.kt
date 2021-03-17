package com.angelorobson.monitorerrorapp.converters

import com.angelorobson.monitorerrorapp.dto.OpsErrorDetailsDto
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel

class OpsErrorDetailsConverter {

    fun convert(details: OpsErrorDetailsDto) = OpsErrorDetailsModel(
        name = details.name,
        date = details.date
    )
}