package com.angelorobson.monitorerrorapp.usecases

import com.angelorobson.monitorerrorapp.converters.OpsErrorConverter
import com.angelorobson.monitorerrorapp.converters.OpsErrorDetailsConverter
import com.angelorobson.monitorerrorapp.dto.OpsErrorDetailsDto
import com.angelorobson.monitorerrorapp.dto.OpsErrorDto
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.repository.OpsErrorRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test
import java.util.Date

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class OpsErrorsUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: OpsErrorRepository

    private lateinit var opsErrorConverter: OpsErrorConverter

    private lateinit var opsErrorDetailsConverter: OpsErrorDetailsConverter

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var useCase: OpsErrorsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        opsErrorConverter = OpsErrorConverter()
        opsErrorDetailsConverter = OpsErrorDetailsConverter()

        useCase = OpsErrorsUseCase(
            repository,
            opsErrorConverter,
            opsErrorDetailsConverter,
            testDispatcher
        )
    }

    @Test
    fun `getOpsErrors flow emits successfully`() = runBlocking {
        // ARRANGE
        val hour = 4

        val list = listOf(
            OpsErrorModel(
                errorsCount = 5,
                source = "source"
            )
        )

        coEvery { repository.getOpsErrors(hour) } returns listOf(
            OpsErrorDto(
                noErrors = 5,
                source = "source"
            )
        )

        // ACT
        val flow = useCase.getOpsErrors(hour)

        // ASSERT
        flow.collect { item ->
            assertEquals(list, item)
            coVerify(exactly = 1) { repository.getOpsErrors(hour) }
        }
    }

    @Test
    fun `getOpsErrorDetail flow emits successfully`() = runBlocking {
        // ARRANGE
        val source = "source"
        val hour = 4
        val date = Date()

        val list = listOf(
            OpsErrorDetailsModel(
                date = date,
                name = "any"
            )
        )

        coEvery { repository.getOpsErrorDetail(source, hour) } returns listOf(
            OpsErrorDetailsDto(
                date = date,
                name = "any"
            )
        )

        // ACT
        val flow = useCase.getOpsErrorDetails(source, hour)

        // ASSERT
        flow.collect { item ->
            assertEquals(list, item)
            coVerify(exactly = 1) { repository.getOpsErrorDetail(source, hour) }
        }
    }

    @Test
    fun `getOpsError flow emits catch error`() = runBlocking {
        // ARRANGE
        val hour = 4
        val exceptionMsg = "this is a test exception"


        coEvery { repository.getOpsErrors(hour) } answers {
            throw Exception(exceptionMsg)
        }

        // ACT
        val flow = useCase.getOpsErrors(hour)

        // ASSERT
        flow.catch {
            assertEquals(exceptionMsg, it.message)
            coVerify(exactly = 1) { repository.getOpsErrors(hour) }
        }.collect { }
    }

    @Test
    fun `getOpsErrorDetail flow emits catch error`() = runBlocking {
        // ARRANGE
        val source = "source"
        val hour = 4
        val exceptionMsg = "this is a test exception"


        coEvery { repository.getOpsErrorDetail(source, hour) } answers {
            throw Exception(exceptionMsg)
        }

        // ACT
        val flow = useCase.getOpsErrorDetails(source, hour)

        // ASSERT
        flow.catch {
            assertEquals(exceptionMsg, it.message)
            coVerify(exactly = 1) { repository.getOpsErrorDetail(source, hour) }
        }.collect { }
    }

}