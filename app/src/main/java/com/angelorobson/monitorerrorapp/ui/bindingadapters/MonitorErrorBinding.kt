package com.angelorobson.monitorerrorapp.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

class MonitorErrorBinding {

    companion object {

        @BindingAdapter("convertIntToString")
        @JvmStatic
        fun intToText(textView: TextView, number: Int) {
            textView.text = number.toString()
        }
    }
}