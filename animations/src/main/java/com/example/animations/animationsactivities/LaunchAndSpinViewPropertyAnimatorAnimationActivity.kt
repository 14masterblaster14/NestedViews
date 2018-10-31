package com.example.animations.animationsactivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


//Good than LaunchAndSpinAnimatorSetAnimationActivity(i.e. set of multiple animations)  in terms of performance

class LaunchAndSpinViewPropertyAnimatorAnimationActivity : BaseAnimationActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onStartAnimation() {
        rocket.animate()
                .translationY(-screenHeight)
                .rotationBy(360f)
                .setDuration(BaseAnimationActivity.Companion.DEFAULT_ANIMATION_DURATION)
                .start()
    }
}
