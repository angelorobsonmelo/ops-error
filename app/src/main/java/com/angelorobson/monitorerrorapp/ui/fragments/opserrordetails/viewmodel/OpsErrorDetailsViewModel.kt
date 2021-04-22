package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.usecases.OpsErrorsUseCase
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OpsErrorDetailsViewModel(private val useCase: OpsErrorsUseCase) : ViewModel() {


    private val _getErrorDetailsResponse =
        MutableLiveData<NetworkResult<List<OpsErrorDetailsModel>>>()
    val getErrorDetailsResponse: LiveData<NetworkResult<List<OpsErrorDetailsModel>>> get() = _getErrorDetailsResponse

    fun getOpsErrorDetails(source: String, hours: Int) {
        viewModelScope.launch {
            useCase.getOpsErrorDetails(source, hours)
                .onStart {
                    _getErrorDetailsResponse.value = NetworkResult.Loading()
                }
                .catch {
                    _getErrorDetailsResponse.value = NetworkResult.Error(it.message)
                }
                .collect {
                    _getErrorDetailsResponse.value = NetworkResult.Success(it)
                }
        }
    }
}