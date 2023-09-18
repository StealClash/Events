package org.hyrical.events.events

import co.aikar.commands.BaseCommand
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

abstract class Event {

    abstract fun getName(): String
    abstract fun getScoreboardLines(): MutableList<String>

    abstract fun getCommands(): ArrayList<BaseCommand>
    abstract fun getListeners(): ArrayList<Listener>

    open fun startEvent(player: Player){}
    open fun startEvent(player: Player, target: Player){}

}