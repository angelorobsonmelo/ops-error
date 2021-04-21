package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.databinding.FragmentOpsErrorDetailsBinding
import com.angelorobson.monitorerrorapp.di.loadMonitorErrorModules
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.ui.MainActivity
import com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.adapter.OpsErrorDetailsAdapter
import com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails.viewmodel.OpsErrorDetailsViewModel
import com.angelorobson.monitorerrorapp.utils.NetworkResult
import com.angelorobson.monitorerrorapp.utils.extensions.android.gone
import com.angelorobson.monitorerrorapp.utils.extensions.android.visible
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
        if (activity is MainActivity) {
            (activity as MainActivity?)?.supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
            }
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

        getErrorDetails()
        bindViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.ops_error_details_menu, menu)
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

    private fun populateScreen(result: List<OpsErrorDetailsModel>?) {
        binding.opsErrorTotalTextView.text = HtmlCompat.fromHtml(
            getString(R.string.ops_error_details_info, result?.size, args.hours),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        mAdapter.submitList(result)
    }

    private fun showShimmerEffect() {
        binding.opsErrorDetailsRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.opsErrorDetailsRecyclerView.hideShimmer()
    }

    private fun hideButtonTryAgain() {
        binding.opsErrorDetailsTryAgainButton.gone()
    }

    private fun showButtonTryAgain() {
        binding.opsErrorDetailsTryAgainButton.visible()
    }

    private fun showGroupMainView() {
        binding.groupErrorsDetailMainView.visible()
    }

    private fun hideGroupMainView() {
        binding.groupErrorsDetailMainView.gone()
    }

}