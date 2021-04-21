package com.angelorobson.monitorerrorapp.ui.fragments.filterhour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.databinding.FragmentFilterHourBinding
import com.angelorobson.monitorerrorapp.utils.NavigationNavigator
import org.koin.android.ext.android.inject


class FilterHourDialogFragment : DialogFragment(), AdapterView.OnItemClickListener {

    private lateinit var binding: FragmentFilterHourBinding
    private val navigator: NavigationNavigator by inject()
    private val args by navArgs<FilterHourDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterHourBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDropDown()
    }

    private fun setupDropDown() {
        val stringArray = resources.getStringArray(R.array.hours_array)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            stringArray
        )

        val editTextFilledExposedDropdown = binding.outlinedExposedDropdown
        editTextFilledExposedDropdown.setText(args.hour.toString())
        editTextFilledExposedDropdown.setAdapter(adapter)

        editTextFilledExposedDropdown.onItemClickListener = this
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onItemClick(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val item: String = parent?.getItemAtPosition(position).toString()

        navigator.to(
            FilterHourDialogFragmentDirections.filterHourFragmentToOpsErrorFragment(item.toInt())
        )
    }

}