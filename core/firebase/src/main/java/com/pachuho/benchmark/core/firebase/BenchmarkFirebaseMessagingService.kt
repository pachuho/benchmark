package com.pachuho.benchmark.core.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pachuho.benchmark.core.datastore.datasource.FirebaseTokenPreferencesDataSource
import com.pachuho.benchmark.core.eventbus.EventBus
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

    @Inject
    lateinit var eventBus: EventBus

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
                    remoteMessage.notification?.title.toString(),
                    remoteMessage.notification?.body.toString()
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

    private fun hasEmpty(vararg values: String): Boolean {
        return values.any { it.trim().isBlank() }
    }

    private fun sendNotification(title: String, body: String) {
        if(hasEmpty(title, body)) {
            EventBus.Event.Message(R.string.invalid_message)
        } else {
            EventBus.Event.Popup(title, body)
        }.let {
            eventBus.emit(it)
        }
    }
}