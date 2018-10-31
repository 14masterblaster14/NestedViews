package com.example.animations.animationsactivities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class LaunchAndSpinAnimatorSetAnimationActivity : BaseAnimationActivity() {

    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
     }*/

    override fun onStartAnimation() {

        val positionAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

        positionAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            rocket.translationY = value
        }

        val rotationAnimator = ObjectAnimator.ofFloat(rocket, "rotation", 0f, 180f)

        val animatorSet = AnimatorSet()
        animatorSet.play(positionAnimator).with(rotationAnimator)

        animatorSet.duration = BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION
        animatorSet.start()

    }


}
