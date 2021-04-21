package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.databinding.FragmentOpsErrorBinding
import com.angelorobson.monitorerrorapp.di.loadMonitorErrorModules
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.adapter.OpsErrorAdapter
import com.angelorobson.monitorerrorapp.ui.fragments.opserror.viewmodel.OpsErrorsViewModel
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import com.angelorobson.monitorerrorapp.utils.extensions.android.gone
import com.angelorobson.monitorerrorapp.utils.extensions.android.visible
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class OpsErrorFragment : Fragment() {

    private val viewModel: OpsErrorsViewModel by viewModel()
    private lateinit var binding: FragmentOpsErrorBinding
    private lateinit var mLayoutManager: LinearLayoutManager
    private val args by navArgs<OpsErrorFragmentArgs>()

    private val mAdapter by lazy {
        OpsErrorAdapter {
            viewModel.navigateToOpsErrorDetails(it.source, args.hour)
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
        when (item.itemId) {
            R.id.menu_refresh -> {
                getOpsErrors()
            }
            R.id.menu_filter -> {
                viewModel.navigateToFilterHour(args.hour)
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

        getOpsErrors()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.getErrorResponse.observe(viewLifecycleOwner, { result ->
            when (result) {
                is NetworkResult.Error -> {
                    hideGroupMainView()
                    showButtonTryAgain()
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    hideButtonTryAgain()
                    showGroupMainView()
                    showShimmerEffect()
                }
                is NetworkResult.Success -> {
                    showGroupMainView()
                    hideButtonTryAgain()
                    hideShimmerEffect()
                    populateScreen(result.data)
                }
            }
        })
    }

    private fun populateScreen(result: List<OpsErrorModel>?) {
        binding.opsErrorTotalTextView.text = HtmlCompat.fromHtml(
            getString(R.string.ops_error_info, result?.size, args.hour),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        mAdapter.submitList(result)
    }

    private fun getOpsErrors() {
        viewModel.getOpsErrors(args.hour)
    }

    private fun hideButtonTryAgain() {
        binding.opsErrorTryAgainButton.gone()
    }

    private fun showButtonTryAgain() {
        binding.opsErrorTryAgainButton.visible()
    }

    private fun showGroupMainView() {
        binding.groupOpsErrorMainView.visible()
    }

    private fun hideGroupMainView() {
        binding.groupOpsErrorMainView.gone()
    }

    private fun showShimmerEffect() {
        binding.opsErrorRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.opsErrorRecyclerView.hideShimmer()
    }

}