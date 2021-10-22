package com.mtj.common.util

import android.util.Log

object Lg {
    private const val TAG = "MTJ"
    private const val isDebug = true
    fun lgi(msg: String) {
        if (isDebug) {
            Log.i(TAG, msg)
        }
    }

    fun lgd(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }

    fun lge(msg: String) {
        if (isDebug) {
            Log.e(TAG, msg)
        }
    }
}