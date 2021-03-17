package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.R
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

        setHasOptionsMenu(true)
        initClickListener()
        setupRecyclerView()
        return binding.root
    }

    private fun initClickListener() {
        binding.opsErrorDetailsTryAgainButton.setOnClickListener {
            getErrorDetails()
        }
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

        getErrorDetails()
        bindViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ops_error_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                getErrorDetails()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getErrorDetails() {
        viewModel.getOpsErrorDetails(source = args.source, hours = args.hours)
    }

    private fun bindViewModel() {
        viewModel.getErrorDetailsResponse.observe(viewLifecycleOwner, { result ->
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

    private fun showShimmerEffect() {
        binding.opsErrorDetailsRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.opsErrorDetailsRecyclerView.hideShimmer()
    }

    private fun hideButtonTryAgain() {
        binding.opsErrorDetailsTryAgainButton.visibility = View.GONE
    }

    private fun showButtonTryAgain() {
        binding.opsErrorDetailsTryAgainButton.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        binding.opsErrorDetailsRecyclerView.visibility = View.VISIBLE
    }

    private fun hideRecyclerView() {
        binding.opsErrorDetailsRecyclerView.visibility = View.GONE
    }

}