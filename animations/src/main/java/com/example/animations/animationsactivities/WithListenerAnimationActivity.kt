package com.example.animations.animationsactivities

import android.animation.Animator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*

class WithListenerAnimationActivity : BaseAnimationActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onStartAnimation() {

        val animator = ValueAnimator.ofFloat(0f, -screenHeight)

        animator.addUpdateListener {
            val value = it.animatedValue as Float
            rocket.translationY = value
            dog.translationY = value
        }

        animator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator?) {
                Toast.makeText(applicationContext, "dog took off", Toast.LENGTH_SHORT).show()
            }

            override fun onAnimationEnd(animation: Animator?) {
                Toast.makeText(applicationContext, "Dog is on the moon", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationCancel(animation: Animator?) {}
        })

        animator.duration = 5000L
        animator.start()
    }
}
