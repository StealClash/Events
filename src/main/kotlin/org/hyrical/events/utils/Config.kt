package org.hyrical.events.utils

import org.hyrical.events.EventsServer

fun saveToConfig(key: String, value: Any?) {
    EventsServer.instance.config.set(key, value)
}

fun <T> fromConfig(key: String): T? {
    return EventsServer.instance.config.get(key) as T
}