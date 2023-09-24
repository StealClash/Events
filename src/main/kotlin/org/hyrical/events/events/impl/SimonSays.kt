package org.hyrical.events.events.impl

import co.aikar.commands.BaseCommand
import org.bukkit.event.Listener
import org.hyrical.events.events.Event

object SimonSays : Event() {

    override fun getName(): String {
        return "SimonSays"
    }

    override fun getDisplayName(): String {
        return "Simon Says"
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
}