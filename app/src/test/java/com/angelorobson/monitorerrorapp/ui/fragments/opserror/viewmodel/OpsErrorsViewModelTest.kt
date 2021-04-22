package com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import com.angelorobson.monitorerrorapp.utils.NavigationNavigator
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class OpsErrorsViewModelTest {


    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: OpsErrorsUseCase

    @RelaxedMockK
    private lateinit var navigator: NavigationNavigator

    @RelaxedMockK
    private lateinit var stateObserver: Observer<NetworkResult<List<OpsErrorModel>>>

    @InjectMockKs
    private lateinit var viewModel: OpsErrorsViewModel

    private val list = listOf(
        OpsErrorModel(
            source = "souce",
            errorsCount = 5
        )
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)

        viewModel.getErrorResponse.observeForever(stateObserver)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getOpsErrors_WhenSuccess_verifySequenceLoadingAndSuccess() {
        // ARRANGE
        coEvery { useCase.getOpsErrors(4) } returns flowOf(list)

        // ACT
        viewModel.getOpsErrors(4)

        // ASSERT
        verifySequence {
            stateObserver.onChanged(NetworkResult.Loading())
            stateObserver.onChanged(NetworkResult.Success(list))
        }
    }

    @Test
    fun getAutoList_WhenError_verifySequenceLoadingAndError() = runBlocking {
        // ARRANGE
        val error = "error"
        coEvery { useCase.getOpsErrors(4) } returns callbackFlow { throw Exception(error) }

        // ACT
        viewModel.getOpsErrors(4)

        // ASSERT
        verifySequence {
            stateObserver.onChanged(NetworkResult.Loading())
            stateObserver.onChanged(NetworkResult.Error(error))
        }
    }

}

