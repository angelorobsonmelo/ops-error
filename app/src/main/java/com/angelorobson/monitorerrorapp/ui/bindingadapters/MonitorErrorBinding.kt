package com.angelorobson.monitorerrorapp.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.angelorobson.monitorerrorapp.utils.formatDateTime
import java.util.*

class MonitorErrorBinding {

    companion object {

        @BindingAdapter("convertIntToString")
        @JvmStatic
        fun intToText(textView: TextView, number: Int) {
            textView.text = number.toString()
        }

        @BindingAdapter("convertToFormatDateTime")
        @JvmStatic
        fun convertToFormatDateTime(textView: TextView, date: Date?) {
            textView.text = date?.formatDateTime()
        }
    }
}