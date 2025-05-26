package com.pachuho.benchmark

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pachuho.benchmark.core.datastore.datasource.FirebaseTokenPreferencesDataSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class BenchmarkFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var preferencesDataSource: FirebaseTokenPreferencesDataSource

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: ${remoteMessage.data}")
            sendNotification(
                remoteMessage.data["title"].toString(),
                remoteMessage.data["body"].toString()
            )
        } else {
            remoteMessage.notification?.let {
                sendNotification(
                    remoteMessage.notification!!.title.toString(),
                    remoteMessage.notification!!.body.toString()
                )
            }
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("onNewToken: $token")
        super.onNewToken(token)

        CoroutineScope(Dispatchers.IO).launch {
            preferencesDataSource.updateFirebaseToken(token)
        }
    }

    private fun sendNotification(title: String, body: String) {
        Timber.d("sendNotification: $title, body:$body")

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val CHANNEL_ID = "fcm_benchmark_channel"
        private const val CHANNEL_NAME = "fcm_benchmark_title"
    }
}