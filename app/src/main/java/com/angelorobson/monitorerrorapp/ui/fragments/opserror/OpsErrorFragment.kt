package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.di.MonitorErrorComponent
import com.angelorobson.monitorerrorapp.ui.viewmodels.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class OpsErrorFragment : Fragment() {

    private val viewModel: OpsErrorsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ops_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MonitorErrorComponent.inject()

        viewModel.getOpsErrors(4)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getErrorResponse.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Error -> {
                    Log.d("NetworkResult.Error", "NetworkResult.Error")
                }
                is NetworkResult.Loading -> {
                    Log.d("NetworkResult.Loading", "NetworkResult.Loading")
                }
                is NetworkResult.Success -> {
                    Log.d("NetworkResult.Success", "NetworkResult.Success")

                }
            }
        })
    }

}