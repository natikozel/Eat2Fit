package com.example.finalproj.util

import android.content.Context
import android.content.SharedPreferences

object FirstRunUtil {
    private const val PREFS_NAME = "com.example.finalproj.prefs"
    private const val KEY_FIRST_RUN = "isFirstRun"

    fun isFirstRun(context: Context): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_FIRST_RUN, true)
    }

    fun updateFirstRunFlag(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply()
    }
}