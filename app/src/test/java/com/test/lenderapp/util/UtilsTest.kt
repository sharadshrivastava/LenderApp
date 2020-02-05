package com.test.lenderapp.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert.assertTrue

@RunWith(MockitoJUnitRunner::class)
class UtilsTest {

    @Mock
    var mContext: Context? = null
    @Mock
    var mResources: Resources? = null
    @Mock
    var mDisplayMetrics: DisplayMetrics? = null

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(mContext?.resources).thenReturn(mResources)
        Mockito.`when`(mResources?.displayMetrics).thenReturn(mDisplayMetrics)
        mDisplayMetrics?.density = 5f
    }

    @Test
    fun testPxFromDp() {
        val expected = 80f
        val actual = Utils.pxFromDp(mContext, 16f)
        assertTrue(expected == actual)
    }
}