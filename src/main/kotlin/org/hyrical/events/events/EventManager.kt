package org.hyrical.events.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.hyrical.events.utils.Chat

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

    fun setActiveEvent(event: Event) {
        assert(!this.isEventActive()) { "You cannot start an event whilst one is already started." }

        this.activeEvent = event

        // Broadcast message
        Bukkit.broadcastMessage(Chat.format("&#FF5C82&lS&#FE6689&lT&#FD7091&lE&#FC7A98&lA&#FB839F&lL&#FA8DA7&lC&#F997AE&lL&#F8A1B5&lA&#F8ABBD&lS&#F7B5C4&lH &#F6BFCB&lE&#F5C9D3&lV&#F4D2DA&lE&#F3DCE1&lN&#F2E6E9&lT&#F1F0F0&lS"))
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcast(
            Component.text("An event has been activated!!", NamedTextColor.YELLOW)
        )
    }
}