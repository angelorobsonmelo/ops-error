package com.angelorobson.monitorerrorapp.repository.remote

import com.angelorobson.monitorerrorapp.dto.OpsErrorDetailsDto
import com.angelorobson.monitorerrorapp.dto.OpsErrorDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpsErrorApi {

    @GET("api/OpsErrors/{hour}")
    suspend fun getOpsErrors(@Path("hour") hour: Int): List<OpsErrorDto>

    @GET("api/OpsErrors/{source}/errors")
    suspend fun getOpsErrorDetail(
        @Path("source") source: String,
        @Query("hours") hour: Int
    ): List<OpsErrorDetailsDto>
}