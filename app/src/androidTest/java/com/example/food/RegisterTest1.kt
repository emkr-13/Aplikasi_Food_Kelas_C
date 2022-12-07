package com.example.food


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterTest1 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(Register::class.java)

    @Test
    fun registerTest1() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.ketikUsername),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.setUsername),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("kelompok 5"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.ketikPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.setPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.ketikPassword),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.setPassword),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("1"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.ketikEmail),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.setEmail),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("a"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.ketikTanggalLahir),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.setTanggalLahir),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("12"), closeSoftKeyboard())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.ketikNomorHp),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.setNomorHp),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("12"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.btnRegister), withText("Register"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
