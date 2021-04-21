package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.angelorobson.monitorerrorapp.BaseRobot
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.withRecyclerView
import org.hamcrest.CoreMatchers

fun opsErrorRobot(func: OpsErrorRobot.() -> Unit) = OpsErrorRobot().apply(func)

class OpsErrorRobot : BaseRobot() {

    fun visibleOpsErrorRecycler() {
        isVisible(R.id.ops_error_recyclerView)
    }

    fun visibleOpsErrorTotalTextView() {
        isVisible(R.id.ops_error_total_textView)
    }

    fun visibleCountTextView(position: Int = 0) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_recyclerView).atPositionOnView(
                        position,
                        R.id.ops_error_row_source_textView
                    )
                ),
                withId(R.id.ops_error_row_source_textView),
                isDisplayed()
            )
        )
    }

    fun visibleSourceErrorTextView(position: Int = 0) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_recyclerView).atPositionOnView(
                        position,
                        R.id.placeholder_ops_error_row_error_count_textView
                    )
                ),
                withId(R.id.placeholder_ops_error_row_error_count_textView),
                isDisplayed()
            )
        )
    }

    fun visibleArrowForwardImageView(position: Int = 0) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_recyclerView).atPositionOnView(
                        position,
                        R.id.ops_error_arrow_forward_image_view
                    )
                ),
                withId(R.id.ops_error_arrow_forward_image_view),
                isDisplayed()
            )
        )
    }

    fun notVisibleButtonTryAgain() {
        isNotVisible(R.id.ops_error_try_again_button)
    }

    fun visibleButtonTryAgain() {
        isVisible(R.id.ops_error_try_again_button)
    }


}