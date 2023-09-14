package org.hyrical.events.events

import java.util.UUID

data class Event(
    val startedAt: Long = System.currentTimeMillis(),
    var joinable: Boolean = true,
    var alive: MutableList<String> = mutableListOf(),
    val spectating: MutableList<String> = mutableListOf(),
    var name: String,
    var startedBy: String
)