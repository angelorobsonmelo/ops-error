package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.angelorobson.monitorerrorapp.databinding.FragmentOpsErrorDetailsBinding
import com.angelorobson.monitorerrorapp.ui.MainActivity
import com.angelorobson.monitorerrorapp.ui.viewmodels.OpsErrorDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class OpsErrorDetailsFragment : Fragment() {

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

}