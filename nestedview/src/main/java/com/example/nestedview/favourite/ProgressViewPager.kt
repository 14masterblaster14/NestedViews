package com.example.nestedview.favourite

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 */
class ProgressViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var isPagingEnabled = true

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {

        if (isPagingEnabled) {

            return super.onInterceptTouchEvent(event)
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (isPagingEnabled) {

            if (event?.action == MotionEvent.ACTION_UP) {

                if (!canScrollHorizontally(1)) {

                    currentItem += 1
                }
            }
            return super.onTouchEvent(event)
        }
        return false
    }
}