package org.hyrical.events.events.impl

import co.aikar.commands.BaseCommand
import org.bukkit.event.Listener
import org.hyrical.events.events.Event

object SwordFFA : Event() {

    override fun getName(): String {
        return "SwordFFA"
    }

    override fun getDisplayName(): String {
        return "Sword FFA"
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