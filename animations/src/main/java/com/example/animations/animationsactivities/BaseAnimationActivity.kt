package com.example.animations.animationsactivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.example.animations.R
import kotlinx.android.synthetic.main.activity_base_animation.*

abstract class BaseAnimationActivity : AppCompatActivity() {

    protected lateinit var rocket: View
    protected lateinit var dog: View
    protected lateinit var frameLayout: View
    protected var screenHeight = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_animation)

        rocket = rockett
        dog = doggy
        frameLayout = container

        frameLayout.setOnClickListener { onStartAnimation() }
    }

    override fun onResume() {
        super.onResume()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
    }

    protected abstract fun onStartAnimation()

    companion object {
        val DEFAULT_ANIMATION_DURATION = 2500L
    }

}
