package com.angelorobson.monitorerrorapp.usecases

import com.angelorobson.monitorerrorapp.converters.OpsErrorConverter
import com.angelorobson.monitorerrorapp.converters.OpsErrorDetailsConverter
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.repository.OpsErrorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class OpsErrorsUseCase(
    private val repository: OpsErrorRepository,
    private val opsErrorConverter: OpsErrorConverter,
    private val opsErrorDetailsConverter: OpsErrorDetailsConverter,
) {

    fun getOpsErrors(hour: Int): Flow<List<OpsErrorModel>> {
        return flow {
            val items =
                repository.getOpsErrors(hour).map { item -> opsErrorConverter.convert(item) }
            emit(items)
        }.flowOn(Dispatchers.IO)
    }

    fun getOpsErrorDetails(source: String, hour: Int): Flow<List<OpsErrorDetailsModel>> {
        return flow {
            val items =
                repository.getOpsErrorDetail(source, hour)
                    .map { item -> opsErrorDetailsConverter.convert(item) }
                    .sortedByDescending { it.date }

            emit(items)
        }.flowOn(Dispatchers.IO)
    }
}