package com.example.lockscreen.lock_screen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import com.example.lockscreen.R

class LockScreenPinIndicator(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    companion object {

        private const val DEFAULT_PIN_LENGTH = 4
    }

    private var mPinLength = DEFAULT_PIN_LENGTH
    private var mPinViews: ArrayList<CheckBox> = ArrayList()
    private lateinit var mPinListener: PinListener


    init {

        val view = LayoutInflater.from(context).inflate(R.layout.lockscreen_pin_indicator, this)
        setupViews()
    }

    private fun setupViews() {

        removeAllViews()

        for (view in 0..mPinLength) {

            var checkbox: CheckBox = LayoutInflater.from(context).inflate(R.layout.lockscreen_pin_checkbox, this) as CheckBox
            var layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            var margin = 5
            layoutParams.setMargins(margin, margin, margin, margin)
            checkbox.layoutParams = layoutParams
            checkbox.isChecked = false
            addView(checkbox)
            mPinViews.add(checkbox)
        }

    }

    interface PinListener {

        fun onCodeCompleted(code: String)
        fun onCodeNotCompleted(code: String)

    }
}