package com.example.finalproj

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import com.example.finalproj.database.DatabaseManager
import com.example.finalproj.ui.theme.Eat2FitTheme
import com.example.finalproj.util.DayCheckUtil
import com.example.finalproj.views.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch


class Eat2FitApp : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseManager.initDb()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, getString(R.string.FCM_FAIL), task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(getString(R.string.Log_TAG), token)
        })
        setContent { Eat2Fit() }

        lifecycleScope.launch {
            //DayCheckUtil.simulateDayPass(this@Eat2FitApp)

            if (DayCheckUtil.hasDayPassed(this@Eat2FitApp)) {
                DayCheckUtil.resetUserData()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom = bottom)
            insets
        }

    }
}

@Composable
fun Eat2Fit() {
    Eat2FitTheme {
        Navigation()
    }
}
