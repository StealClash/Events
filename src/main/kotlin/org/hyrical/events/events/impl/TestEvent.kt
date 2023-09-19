package org.hyrical.events.events.impl

import co.aikar.commands.BaseCommand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager

object TestEvent : Event() {

    override fun getName(): String {
        return "Test"
    }

    override fun getScoreboardLines(): MutableList<String> {
        return mutableListOf()
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf()
    }

    override fun startEvent(player: Player){
        startedAt = System.currentTimeMillis()
        EventManager.currentEvent = this
    }
}