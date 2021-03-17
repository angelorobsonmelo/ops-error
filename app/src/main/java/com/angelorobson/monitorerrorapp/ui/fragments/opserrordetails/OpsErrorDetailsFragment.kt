package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.databinding.FragmentOpsErrorDetailsBinding
import com.angelorobson.monitorerrorapp.di.MonitorErrorComponent
import com.angelorobson.monitorerrorapp.ui.MainActivity
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

//        setupRecyclerView()
        return binding.root
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
                    Log.d("NetworkResult.Error", "NetworkResult.Error")
                }
                is NetworkResult.Loading -> {
                    Log.d("NetworkResult.Loading", "NetworkResult.Loading")
                }
                is NetworkResult.Success -> {
//                    mAdapter.submitList(result.data)
                    Log.d("NetworkResult.Success", "NetworkResult.Success")

                }
            }
        })
    }

}