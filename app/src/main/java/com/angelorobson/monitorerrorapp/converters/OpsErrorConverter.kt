package com.angelorobson.monitorerrorapp.converters

import com.angelorobson.monitorerrorapp.dto.OpsErrorDto
import com.angelorobson.monitorerrorapp.models.OpsErrorModel

class OpsErrorConverter {

    fun convert(opsErrorDto: OpsErrorDto) = OpsErrorModel(
        errorsCount = opsErrorDto.noErrors,
        source = opsErrorDto.source
    )
}