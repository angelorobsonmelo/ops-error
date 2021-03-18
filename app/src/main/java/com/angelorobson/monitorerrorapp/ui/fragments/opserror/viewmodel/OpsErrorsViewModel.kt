package com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.OpsErrorFragmentDirections
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import com.angelorobson.monitorerrorapp.utils.NavigationNavigator
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OpsErrorsViewModel(
    private val useCase: OpsErrorsUseCase,
    private val navigator: NavigationNavigator
) : ViewModel() {

    private val _getErrorResponse = MutableLiveData<NetworkResult<List<OpsErrorModel>>>()
    val getErrorResponse: LiveData<NetworkResult<List<OpsErrorModel>>> get() = _getErrorResponse

    fun getOpsErrors(hours: Int) {
        viewModelScope.launch {
            useCase.getOpsErrors(hours)
                .onStart {
                    _getErrorResponse.value = NetworkResult.Loading()
                }
                .catch {
                    _getErrorResponse.value = NetworkResult.Error(it.message)
                }
                .collect {
                    _getErrorResponse.value = NetworkResult.Success(it)
                }
        }
    }

    fun navigateToFilterHour(hour: Int) {
        navigator.to(OpsErrorFragmentDirections.actionOpsErrorFragmentToFilterHourFragment(hour))
    }

    fun navigateToOpsErrorDetails(source: String, hours: Int) {
        navigator.to(
            OpsErrorFragmentDirections.navigateToOpsErrorDetailsFragment(
                source = source,
                hours = hours,
                title = source
            )
        )
    }

}