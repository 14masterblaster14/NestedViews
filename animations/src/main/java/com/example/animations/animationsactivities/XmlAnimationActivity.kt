package com.example.animations.animationsactivities

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.animations.R

class XmlAnimationActivity : BaseAnimationActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onStartAnimation() {
        val rocketAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
        rocketAnimatorSet.setTarget(rocket)

        val dogAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
        dogAnimatorSet.setTarget(dog)

        val bothAnimatorSet = AnimatorSet()
        bothAnimatorSet.playTogether(rocketAnimatorSet, dogAnimatorSet)

        bothAnimatorSet.duration = BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION
        bothAnimatorSet.start()

    }


}
