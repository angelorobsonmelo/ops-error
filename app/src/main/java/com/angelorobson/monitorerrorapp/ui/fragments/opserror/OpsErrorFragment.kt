package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.databinding.FragmentOpsErrorBinding
import com.angelorobson.monitorerrorapp.di.MonitorErrorComponent
import com.angelorobson.monitorerrorapp.ui.adapters.OpsErrorAdapter
import com.angelorobson.monitorerrorapp.ui.viewmodels.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class OpsErrorFragment : Fragment() {

    private val viewModel: OpsErrorsViewModel by viewModel()
    private lateinit var binding: FragmentOpsErrorBinding
    private lateinit var mLayoutManager: LinearLayoutManager
    private var hour = 4

    private val mAdapter by lazy {
        OpsErrorAdapter {
            viewModel.navigateToOpsErrorDetails(it.source, hour)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpsErrorBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        mLayoutManager = LinearLayoutManager(context)

        binding.opsErrorRecyclerView.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    mLayoutManager.orientation
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MonitorErrorComponent.inject()

        viewModel.getOpsErrors(hour)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getErrorResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is NetworkResult.Error -> {
                    Log.d("NetworkResult.Error", "NetworkResult.Error")
                }
                is NetworkResult.Loading -> {
                    Log.d("NetworkResult.Loading", "NetworkResult.Loading")
                }
                is NetworkResult.Success -> {
                    mAdapter.submitList(result.data)
                    Log.d("NetworkResult.Success", "NetworkResult.Success")

                }
            }
        })
    }

}