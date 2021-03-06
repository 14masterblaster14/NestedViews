package com.example.apirecyclerviewtimber

import android.util.Log
import android.util.Log.ERROR
import timber.log.Timber

/**
 */
class ReleaseTree : Timber.Tree() {

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {


        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        } else if (priority == Log.ERROR || priority == Log.WARN) {

            //yourCrashLibrary.log(priority,tag,message)
            //Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
            //Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
            //Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE,message)
        }

        if (t == null) {
            //Crashlytics.logException(Exception(message))
        } else {
            //Crashlytics.logException(t)

        }
    }
}