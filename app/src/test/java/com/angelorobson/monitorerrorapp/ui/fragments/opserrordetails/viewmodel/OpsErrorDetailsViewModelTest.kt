package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class OpsErrorDetailsViewModelTest {


    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: OpsErrorsUseCase


    @RelaxedMockK
    private lateinit var stateObserver: Observer<NetworkResult<List<OpsErrorDetailsModel>>>

    @InjectMockKs
    private lateinit var viewModel: OpsErrorDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)

        viewModel.getErrorDetailsResponse.observeForever(stateObserver)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getAutoList_WhenSuccess_informAutoListSuccessState() {
        val list = listOf(
            OpsErrorDetailsModel(
                name = "exception",
                date = Date()
            )
        )

        coEvery { useCase.getOpsErrorDetails("source", 4) } returns flowOf(list)

        viewModel.getOpsErrorDetails("source", 4)

        verifySequence {
            stateObserver.onChanged(NetworkResult.Loading())
            stateObserver.onChanged(NetworkResult.Success(list))
        }
    }

}