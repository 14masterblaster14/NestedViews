package com.example.apirecyclerviewtimber

import timber.log.Timber

/**
 */
class CustomDebugTree : Timber.DebugTree() {

    //Sample tree which prints line number along with tag

    override fun createStackElementTag(element: StackTraceElement): String? {
        //return super.createStackElementTag(element)
        return super.createStackElementTag(element) + ":" + element.lineNumber
        //return super.createStackElementTag(element) + " @MasterBlaster : " + element.lineNumber
    }
}