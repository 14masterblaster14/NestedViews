package com.example.basicsanitycheck

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object NetworkConnectionCheckUtil {

    fun isOnline(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting)
        Log.i("MasterBlaster", "Network Status : ${isConnected}")
        return isConnected
    }
}