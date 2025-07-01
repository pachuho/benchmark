package com.pachuho.benchmark.data_test

import com.pachuho.benchmark.core.eventbus.EventBus

class FakeEventBus : EventBus() {
    private val events = mutableListOf<Event>()

    override fun emit(event: Event) {
        events.add(event)
    }
}