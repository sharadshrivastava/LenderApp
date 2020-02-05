package com.test.lenderapp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.test.lenderapp.R
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This class is for Android UI testing using Espresso.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class AccountDetailsFragmentTest {

    private val UILOAD_DELAY = 2000L

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        Thread.sleep(UILOAD_DELAY)
    }

    //Testcase to check whether Jan month is visible or not on the UI.
    @Test
    fun testMonthAvailable() {
        onView(allOf(withId(R.id.month), withParent(withId(R.id.firstMonthChart)))).
            check(matches(isDisplayed())).check(matches(withText("Jan")))
    }

    //Testcase to check whether total expenses of Jan month is correctly displayed or not.
    @Test
    fun testMonthExpense() {
        onView(allOf(withId(R.id.totalAmount), withParent(withId(R.id.firstMonthChart)))).
            check(matches(isDisplayed())).check(matches(withText("$1387.0")))
    }

    //In the same way, using espresso we can test other UI components.
}
