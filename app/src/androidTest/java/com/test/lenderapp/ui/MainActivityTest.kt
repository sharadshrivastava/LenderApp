package com.test.lenderapp.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.test.lenderapp.R

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testFragmentLoaded() {
        val fragment = rule.activity.supportFragmentManager.findFragmentById(R.id.container)
        Assert.assertNotNull(fragment)
    }
}
