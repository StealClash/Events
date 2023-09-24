package org.hyrical.events.events.impl

import co.aikar.commands.BaseCommand
import org.bukkit.event.Listener
import org.hyrical.events.events.Event

object CrystalFFA : Event() {

    override fun getName(): String {
        return "CrystalFFA"
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