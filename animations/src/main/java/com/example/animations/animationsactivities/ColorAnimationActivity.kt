package com.example.animations.animationsactivities

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.animations.R

class ColorAnimationActivity : BaseAnimationActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onStartAnimation() {

        val objectAnimator = ObjectAnimator.ofObject(
                frameLayout,
                "backgroundColor",
                ArgbEvaluator(),
                ContextCompat.getColor(this, R.color.background_from),
                ContextCompat.getColor(this, R.color.background_to))

        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ValueAnimator.REVERSE

        objectAnimator.duration = BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION
        objectAnimator.start()
    }


}
