package org.hyrical.events.events.impl.sumo

import co.aikar.commands.BaseCommand
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.hyrical.events.events.Event

object Sumo : Event() {

    val player1: Player? = null
    val player2: Player? = null

    override fun getName(): String {
        return "Sumo"
    }

    override fun getDisplayName(): String {
        return "Sumo"
    }

    override fun getScoreboardLines(): MutableList<String> {
        if (player1 == null || player2 == null) return mutableListOf()

        return mutableListOf(
            "&b${player1.name} &fvs &b${player2.name}"
        )
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf()
    }


}