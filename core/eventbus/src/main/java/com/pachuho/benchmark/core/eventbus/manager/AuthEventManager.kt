package com.pachuho.benchmark.core.eventbus.manager

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class AuthEventManager @Inject constructor() {
    private val _eventFlow = MutableSharedFlow<AuthEvent>(extraBufferCapacity = 1)
    val eventFlow: SharedFlow<AuthEvent> = _eventFlow

    fun emit(event: AuthEvent) {
        _eventFlow.tryEmit(event)
    }

    sealed class AuthEvent {
        data object ForceLogout : AuthEvent()
    }
}
