package com.example.lockscreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lockscreen.lock_screen.LockScreenConfiguration

class MainActivity : AppCompatActivity() {

    private lateinit var mLockScreenBuilder: LockScreenConfiguration.LockScreenBuilder
    private lateinit var mLockScreenConfiguration: LockScreenConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showLockScreenFragment()
    }

    private fun showLockScreenFragment() {

        mLockScreenBuilder.setDefaultLockScreenBuilder(View.OnClickListener { forgotPassword() })
        mLockScreenConfiguration = LockScreenConfiguration(this, mLockScreenBuilder)
    }

    private fun forgotPassword() {

    }
}
