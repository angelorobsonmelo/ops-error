package com.angelorobson.monitorerrorapp.ui.fragments.opserror

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.angelorobson.monitorerrorapp.BaseRobot
import com.angelorobson.monitorerrorapp.R
import com.angelorobson.monitorerrorapp.withRecyclerView
import org.hamcrest.CoreMatchers

fun opsErrorRobot(func: OpsErrorRobot.() -> Unit) = OpsErrorRobot().apply(func)

class OpsErrorRobot : BaseRobot() {

    fun visibleAutoRecycler() {
        isVisible(R.id.ops_error_recyclerView)
    }

    fun visibleDaysRemaining(position: Int, text: String) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_recyclerView).atPositionOnView(
                        position,
                        R.id.ops_error_total_textView
                    )
                ),
                withId(R.id.ops_error_total_textView),
                isDisplayed()
            )
        ).check(matches(withText(text)))
    }

    private fun visibleDescendantMessageAlert(id: Int, position: Int) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_recyclerView).atPositionOnView(
                        position,
                        R.id.ops_error_total_textView
                    )
                ),
                withId(id),
                isDisplayed()
            )
        )
    }

    fun visibleCountTextView(position: Int = 0) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(
                    withRecyclerView(R.id.ops_error_recyclerView).atPositionOnView(
                        position,
                        R.id.placeholder_ops_error_row_source_textView
                    )
                ),
                withId(R.id.placeholder_ops_error_row_source_textView),
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
                        R.id.opsErrorArrowForwardImageView
                    )
                ),
                withId(R.id.opsErrorArrowForwardImageView),
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

    fun totalErrorsVisible() {
        isVisible(R.id.ops_error_total_textView)
    }


}