package com.angelorobson.monitorerrorapp.ui.fragments.opserror.adapter

import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.databinding.OpsErrorRowLayoutBinding
import com.angelorobson.monitorerrorapp.models.OpsErrorModel
import com.angelorobson.monitorerrorapp.utils.BindingAdapter

class OpsErrorAdapter(private val onClick: (OpsErrorModel) -> Unit) :
    BindingAdapter<OpsErrorModel, OpsErrorRowLayoutBinding>() {

    override fun getLayoutResId(): Int = R.layout.ops_error_row_layout

    override fun onBindViewHolder(binding: OpsErrorRowLayoutBinding, position: Int) {
        binding.run {
            val opsError = getItem(position)
            item = opsError
            opsErrorRowMaterialCardView.setOnClickListener {
                onClick.invoke(opsError)
            }
            executePendingBindings()
        }
    }
}