package com.example.lockscreen.lock_screen

import android.content.Context
import android.view.View

class LockScreenConfiguration(var context: Context, var mLockScreenBuilder: LockScreenBuilder) {


    companion object LockScreenBuilder {
        var mMode = Mode.CREATE
        var mTitle = "Unlock with your pin code or fingerprint"
        var mLeftButtonLabel = "Can't remember"
        var mOnLeftButtonClickListener: View.OnClickListener? = null
        var mUseFingerprint = false
        var mCodeLength = CodeLength.SHORT

        fun setDefaultLockScreenBuilder(leftButtonClickListener: View.OnClickListener) {

            mOnLeftButtonClickListener = leftButtonClickListener
        }

        fun setLockScreenBuilder(mode: Mode, title: String, leftButtonLabel: String, leftButtonClickListener: View.OnClickListener, useFingerPrint: Boolean, codeLength: CodeLength) {
            mMode = mode
            mTitle = title
            mLeftButtonLabel = leftButtonLabel
            mOnLeftButtonClickListener = leftButtonClickListener
            mUseFingerprint = useFingerPrint
            mCodeLength = codeLength
        }
    }

    fun showLockScreen() {

        if (mMode == Mode.CREATE) {

            // Create Pin and stored it


        } else {
            // Pin already Exists, just verify it

        }


    }


}

enum class CodeLength(val length: Int) {

    SHORT(4),
    LONG(6)
}

enum class Mode(val operationMode: Int) {

    CREATE(0),
    AUTHORIZE(1)
}

