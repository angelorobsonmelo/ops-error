package com.angelorobson.monitorerrorapp.ui.fragments.opserrordetails

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.angelorobson.monitorerrorapp.BaseRobot
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.withRecyclerView
import org.hamcrest.CoreMatchers

fun opsErrorDetailsRobot(func: OpsErrorDetailsRobot.() -> Unit) = OpsErrorDetailsRobot().apply(func)

class OpsErrorDetailsRobot : BaseRobot() {

    fun visibleOpsErrorDetailsRecycler() {
        isVisible(R.id.ops_error_details_recyclerView)
    }

    fun visibleDateTextView(position: Int = 0) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_details_recyclerView).atPositionOnView(
                        position,
                        R.id.placeholder_ops_error_details_row_error_count_textView
                    )
                ),
                withId(R.id.placeholder_ops_error_details_row_error_count_textView),
                isDisplayed()
            )
        )
    }

    fun visibleSourceErrorTextView(position: Int = 0) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_details_recyclerView).atPositionOnView(
                        position,
                        R.id.placeholder_ops_error_details_row_source_textView
                    )
                ),
                withId(R.id.placeholder_ops_error_details_row_source_textView),
                isDisplayed()
            )
        )
    }

    fun notVisibleButtonTryAgain() {
        isNotVisible(R.id.ops_error_details_try_again_button)
    }

    fun visibleButtonTryAgain() {
        isVisible(R.id.ops_error_details_try_again_button)
    }


}