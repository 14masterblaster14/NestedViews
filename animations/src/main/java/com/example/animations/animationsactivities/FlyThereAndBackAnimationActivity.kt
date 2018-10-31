package com.example.animations.animationsactivities

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class FlyThereAndBackAnimationActivity : BaseAnimationActivity() {

    /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onStartAnimation() {

        val aniamtor = ValueAnimator.ofFloat(0f, -screenHeight)

        aniamtor.addUpdateListener {

            val value = it.animatedValue as Float
            rocket.translationY = value
            dog.translationY = value
        }

        aniamtor.repeatMode = ValueAnimator.REVERSE
        aniamtor.repeatCount = 3

        aniamtor.duration = 700L
        aniamtor.start()
    }


}
