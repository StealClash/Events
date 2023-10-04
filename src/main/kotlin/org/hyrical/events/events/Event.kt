package org.hyrical.events.events

import co.aikar.commands.BaseCommand
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

abstract class Event {

    abstract fun getName(): String
    abstract fun getDisplayName(): String
    abstract fun getScoreboardLines(): MutableList<String>

    abstract fun getCommands(): ArrayList<BaseCommand>
    abstract fun getListeners(): ArrayList<Listener>
    abstract fun getDeathMessage(player: Player, killer: Player?): String

    open fun giveKitOnStart(): String { return "" }

    open fun startEvent(){}
    open fun endEvent(){}

    open fun revive(player: Player){}

}