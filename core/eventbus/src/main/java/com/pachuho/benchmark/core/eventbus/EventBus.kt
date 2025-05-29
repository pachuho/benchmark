package com.pachuho.benchmark.core.eventbus

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class EventBus @Inject constructor() {
    private val _eventFlow = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val eventFlow: SharedFlow<Event> = _eventFlow

    fun emit(event: Event) {
        _eventFlow.tryEmit(event)
    }

    sealed class Event {
        data object Logout : Event()
        data class Message(@StringRes val messageRes: Int) : Event()
        data class Popup(
            val title: String,
            val body: String
        ): Event()
    }
}