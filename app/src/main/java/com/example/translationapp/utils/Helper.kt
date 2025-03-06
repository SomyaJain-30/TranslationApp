package com.example.translationapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Helper {

    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                ) || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        }
        return true
    }

}