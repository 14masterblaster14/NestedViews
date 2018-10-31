package com.example.animations.animationsactivities

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class LaunchRocketObjectAnimatorAnimationActivity : BaseAnimationActivity() {

    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
     }*/

    override fun onStartAnimation() {

        //Note: There’s a limitation to ObjectAnimator — it can’t animate two objects simultaneously.
        // To work around it, you create two instances of ObjectAnimator.

        val objectAnimator = ObjectAnimator.ofFloat(rocket, "translationY", 0f, -screenHeight)

        //rocket is the object to animate. The object must have a property corresponding to the name of the property
        // you wish to change, which in this example is “translationY”.
        // You’re able to do this because rocket is an object of class View, which, in its base Java class,
        // has an accessible setter with setTranslationY().

        objectAnimator.duration = BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION
        objectAnimator.start()
    }

}
