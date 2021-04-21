package com.angelorobson.monitorerrorapp.ui.bindingadapters

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.utils.extensions.date.formatDateTime
import java.util.*

class MonitorErrorBinding {

    companion object {

        @BindingAdapter("convertIntToString")
        @JvmStatic
        fun intToText(textView: TextView, number: Int) {
            textView.text = number.toString()
        }

        @BindingAdapter("totalExceptions")
        @JvmStatic
        fun totalExceptions(textView: TextView, number: Int) {
            val text = HtmlCompat.fromHtml(
                textView.context.getString(R.string.total_of_exceptions, number),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            textView.text = text
        }

        @BindingAdapter("convertToFormatDateTime")
        @JvmStatic
        fun convertToFormatDateTime(textView: TextView, date: Date?) {
            textView.text = date?.formatDateTime()
        }
    }
}