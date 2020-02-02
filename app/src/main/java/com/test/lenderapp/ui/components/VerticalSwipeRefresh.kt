package com.test.lenderapp.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Normal SwipeRefreshLayout interrupts list horizontal scrolling.
 * So we need to use this class for smooth scrolling of both list and refresh.
 */
class VerticalSwipeRefresh(context: Context, attrs: AttributeSet) :
    SwipeRefreshLayout(context, attrs) {

    private var mTouchSlop: Int = 0
    private var mPrevX: Float = 0f

    init {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> mPrevX = MotionEvent.obtain(event).x
            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = Math.abs(eventX - mPrevX)

                if (xDiff > mTouchSlop) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }
}