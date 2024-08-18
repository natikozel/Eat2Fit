package com.example.finalproj.database

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.example.finalproj.Eat2FitApp
import com.example.finalproj.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(getString(R.string.Log_TAG), "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(getString(R.string.Log_TAG), "Message data payload: ${remoteMessage.data}")
            if (needsToBeScheduled()) {
                // pass
            } else {
                handleNow()
            }
        }

        remoteMessage.notification?.let {
            Log.d(getString(R.string.Log_TAG), "Message Notification Body: ${it.body}")
        }
        if (remoteMessage.getNotification() != null) {
            showNotification(
                remoteMessage.getNotification()!!.title!!,
                remoteMessage.getNotification()!!.body!!
            )
        }

    }


    fun forceTokenRefresh() {
        FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { newTokenTask ->
                    if (newTokenTask.isSuccessful) {
                        val newToken = newTokenTask.result
                        Log.d("TokenManager", "New FCM Token: $newToken")
                        // Send the new token to your backend
                    } else {
                        Log.e("TokenManager", "Failed to get new FCM token", newTokenTask.exception)
                    }
                }
            } else {
                Log.e("TokenManager", "Failed to delete FCM token", task.exception)
            }
        }
    }

    private fun showNotification(
        title: String,
        message: String
    ) {
        val intent = Intent(this, Eat2FitApp::class.java)
        val channel_id = "notification_channel"

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )


        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            channel_id
        )
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContentTitle(title).setContentText(message)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channel_id, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager!!.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager!!.notify(0, builder.build())
    }

    private fun needsToBeScheduled() = true

    override fun onNewToken(token: String) {
        Log.d(getString(R.string.Log_TAG), "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun handleNow() {
        Log.d(getString(R.string.Log_TAG), "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        val deviceToken = hashMapOf(
            "token" to token,
            "timestamp" to FieldValue.serverTimestamp(),
        )
        AuthenticationManager.getCurrentUser()?.uid?.let {
            Firebase.firestore.collection("fcmTokens").document(it)
                .set(deviceToken)
        }
        Log.d(getString(R.string.Log_TAG), "sendRegistrationTokenToServer($token)")
    }


}