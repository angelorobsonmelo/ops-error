package com.angelorobson.monitorerrorapp.ui.adapters

import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.databinding.OpsErrorDetailsRowLayoutBinding
import com.angelorobson.monitorerrorapp.models.OpsErrorDetailsModel
import com.angelorobson.monitorerrorapp.utils.BindingAdapter

class OpsErrorDetailsAdapter :
    BindingAdapter<OpsErrorDetailsModel, OpsErrorDetailsRowLayoutBinding>() {

    override fun getLayoutResId(): Int = R.layout.ops_error_details_row_layout

    override fun onBindViewHolder(binding: OpsErrorDetailsRowLayoutBinding, position: Int) {
        binding.run {
            val opsErrorDetails = getItem(position)
            item = opsErrorDetails

            executePendingBindings()
        }
    }
}