package com.example.nestedview

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 *
 */
class SwipeLockableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    var isSwipeLocked: Boolean = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return !isSwipeLocked && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !isSwipeLocked && super.onInterceptTouchEvent(ev)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return !isSwipeLocked && super.canScrollHorizontally(direction)
    }
}