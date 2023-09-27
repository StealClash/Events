package org.hyrical.events.listeners.customevents

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class EventKill(val killer: Player, val victim: Player) : Event()  {
    private val handlerList = HandlerList()

    override fun getHandlers(): HandlerList {
        return handlerList
    }
}