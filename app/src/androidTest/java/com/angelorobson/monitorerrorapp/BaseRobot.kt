package com.angelorobson.monitorerrorapp

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

open class BaseRobot {

    fun click(viewId: Int) {
        onView(withId(viewId))
            .perform(click())
    }

    fun swipeUp(viewId: Int){
        onView(withId(viewId)).perform(swipeUp())
    }

    fun isVisible(viewId: Int) {
        onView(withId(viewId)).check(matches(isDisplayed()))
    }

    fun isNotVisible(viewId: Int) {
        onView(withId(viewId)).check(matches(not(isDisplayed())))
    }

    fun isKeyboardShown(): Boolean {
        val inputMethodManager =
            InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        return inputMethodManager.isAcceptingText
    }

    fun isKeyboardNotShow() =
        not(isKeyboardShown())


    fun toggleKeyboard() {
        val inputMethodManager =
            InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun fillField(viewId: Int, text: String) {
        onView(withId(viewId))
            .perform(clearText(), typeText(text))
            .perform(closeSoftKeyboard())
    }

    fun ViewInteraction.hasEmptyText() {
        this.check(matches(withText("")))
    }

//    fun takeLayoutScreenshot(layout: Int, fileName: String) {
//        onView(withId(layout))
//            .perform(ViewActionUtils.takeScreenshot(fileName))
//    }

    fun forceClick(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(
                    ViewMatchers.isClickable(),
                    ViewMatchers.isEnabled(),
                    isDisplayed()
                )
            }

            override fun getDescription(): String {
                return "force click"
            }

            override fun perform(uiController: UiController, view: View) {
                view.performClick() // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle()
            }
        }
    }
}