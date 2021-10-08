package com.nicomahnic.basic_esp_provisioning.utils

import android.content.Context
import android.net.ConnectivityManager

object Utils {

    fun checkForInternet(context: Context): Boolean {

        val connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}