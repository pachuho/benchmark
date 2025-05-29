package com.pachuho.benchmark.core.data.auth

import com.pachuho.benchmark.core.eventbus.EventBus
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenProvider: AuthTokenProvider,
    private val eventBus: EventBus
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) {
            eventBus.emit(EventBus.Event.Logout)
            return null
        }

        val success = runBlocking { tokenProvider.reissue() }

        return if (success) {
            val newToken = runBlocking { tokenProvider.getAccessToken() }
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            runBlocking { tokenProvider.logout() }
            eventBus.emit(EventBus.Event.Logout)
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
