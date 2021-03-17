package com.angelorobson.monitorerrorapp.repository.remote

import com.angelorobson.monitorerrorapp.utils.WebService

internal class OpsErrorService : WebService<OpsErrorApi>() {

    override fun api(): OpsErrorApi = getInstance().create(OpsErrorApi::class.java)
}