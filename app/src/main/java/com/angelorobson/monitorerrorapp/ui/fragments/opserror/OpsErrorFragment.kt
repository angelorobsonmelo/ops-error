package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
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

        setHasOptionsMenu(true)
        setupRecyclerView()
        initClickListener()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ops_error_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId) {
             R.id.menu_refresh -> {
                 getOpsErrors()
             }
         }

        return super.onOptionsItemSelected(item)
    }

    private fun initClickListener() {
        binding.opsErrorTryAgainButton.setOnClickListener {
            getOpsErrors()
        }
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

        getOpsErrors()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getErrorResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is NetworkResult.Error -> {
                    hideRecyclerView()
                    showButtonTryAgain()
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    hideButtonTryAgain()
                    showRecyclerView()
                    showShimmerEffect()
                }
                is NetworkResult.Success -> {
                    showRecyclerView()
                    hideButtonTryAgain()
                    hideShimmerEffect()
                    mAdapter.submitList(result.data)
                }
            }
        })
    }

    private fun getOpsErrors() {
        viewModel.getOpsErrors()
    }

    private fun hideButtonTryAgain() {
        binding.opsErrorTryAgainButton.visibility = View.GONE
    }

    private fun showButtonTryAgain() {
        binding.opsErrorTryAgainButton.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        binding.opsErrorRecyclerView.visibility = View.VISIBLE
    }

    private fun hideRecyclerView() {
        binding.opsErrorRecyclerView.visibility = View.GONE
    }

    private fun showShimmerEffect() {
        binding.opsErrorRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.opsErrorRecyclerView.hideShimmer()
    }

}