package org.hyrical.events.events

object EventManager {

    var activeEvent: Event? = null

    fun isEventActive() = activeEvent != null

    fun getCurrentEventName() = if (isEventActive()) {
        activeEvent?.name
    } else {
        "None"
    }

    fun getCurrentAlive() = if (isEventActive()) {
        activeEvent?.alive?.size.toString()
    } else {
        "N/A"
    }

    fun getCurrentSpectators() = if (isEventActive()) {
        activeEvent?.spectating?.size.toString()
    } else {
        "N/A"
    }
}