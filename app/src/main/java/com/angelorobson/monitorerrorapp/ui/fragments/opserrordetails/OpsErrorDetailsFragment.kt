package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.databinding.FragmentOpsErrorDetailsBinding
import com.angelorobson.monitorerrorapp.di.MonitorErrorComponent
import com.angelorobson.monitorerrorapp.ui.MainActivity
import com.angelorobson.monitorerrorapp.ui.adapters.OpsErrorAdapter
import com.angelorobson.monitorerrorapp.ui.adapters.OpsErrorDetailsAdapter
import com.angelorobson.monitorerrorapp.ui.viewmodels.OpsErrorDetailsViewModel
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class OpsErrorDetailsFragment : Fragment() {

    private val args by navArgs<OpsErrorDetailsFragmentArgs>()


    private val viewModel: OpsErrorDetailsViewModel by viewModel()
    private lateinit var binding: FragmentOpsErrorDetailsBinding
    private lateinit var mLayoutManager: LinearLayoutManager

    private val mAdapter by lazy {
        OpsErrorDetailsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpsErrorDetailsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        mLayoutManager = LinearLayoutManager(context)

        binding.opsErrorDetailsRecyclerView.apply {
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

        viewModel.getOpsErrorDetails(source = args.source, hours = args.hours)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getErrorDetailsResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    mAdapter.submitList(result.data)
                }
            }
        })
    }

    private fun showShimmerEffect() {
        binding.opsErrorDetailsRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.opsErrorDetailsRecyclerView.hideShimmer()
    }

}