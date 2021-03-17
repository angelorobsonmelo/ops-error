package com.angelorobson.monitorerrorapp.repository

import com.angelorobson.monitorerrorapp.dto.OpsErrorDetailsDto
import com.angelorobson.monitorerrorapp.dto.OpsErrorDto
import com.angelorobson.monitorerrorapp.repository.remote.OpsErrorApi

class OpsErrorRepository(private val api: OpsErrorApi) {


    suspend fun getOpsErrorDetail(source: String, hour: Int): List<OpsErrorDetailsDto> =
        api.getOpsErrorDetail(source, hour)

    suspend fun getOpsErrors(hour: Int): List<OpsErrorDto> {
        return api.getOpsErrors(hour)
    }
}