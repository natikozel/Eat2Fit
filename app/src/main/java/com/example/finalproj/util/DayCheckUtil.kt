package com.example.finalproj.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.finalproj.database.AuthenticationManager
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.database.DatabaseKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

object DayCheckUtil {
    private const val PREFS_NAME = "DayCheckPrefs"
    private const val LAST_CHECK_KEY = "lastCheckTimestamp"
    private const val TAG = "DayCheckUtil"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    suspend fun hasDayPassed(context: Context): Boolean {
        return withContext(Dispatchers.IO) {
            val prefs = getSharedPreferences(context)
            val lastCheckTimestamp = prefs.getLong(LAST_CHECK_KEY, 0L)
            val currentTimestamp = System.currentTimeMillis()

            Log.d(TAG, "lastCheckTimestamp: $lastCheckTimestamp, currentTimestamp: $currentTimestamp")

            if (lastCheckTimestamp != 0L && currentTimestamp - lastCheckTimestamp >= TimeUnit.DAYS.toMillis(1)) {
                prefs.edit().putLong(LAST_CHECK_KEY, currentTimestamp).apply()
                true
            } else {
                false
            }
        }
    }

    suspend fun resetUserData() {
        withContext(Dispatchers.IO) {
            val userId = AuthenticationManager.getCurrentUser()?.uid ?: return@withContext
            DatabaseManager.updateUserKey(DatabaseKeys.PREVIOUS_MEALS, null)
            Log.d(TAG, "User data reset: previousMeals and maxCalories")
        }
    }

    suspend fun simulateDayPass(context: Context) {
        withContext(Dispatchers.IO) {
            val prefs = getSharedPreferences(context)
            val simulatedTimestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)
            prefs.edit().putLong(LAST_CHECK_KEY, simulatedTimestamp).apply()
            Log.d(TAG, "Simulated day pass: lastCheckTimestamp set to $simulatedTimestamp")
        }
    }
}