package com.pachuho.benchmark.core.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

open class EventBus @Inject constructor() {
    private val _eventFlow = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val eventFlow: SharedFlow<Event> = _eventFlow

    open fun emit(event: Event) {
        _eventFlow.tryEmit(event)
    }

    sealed class Event {
        data object Logout : Event()
        data class Message(val message: String) : Event()
        data class Popup(
            val title: String,
            val body: String
        ): Event()
    }
}