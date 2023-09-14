package org.hyrical.events

import co.aikar.commands.PaperCommandManager
import org.bukkit.plugin.java.JavaPlugin

class EventsServer : JavaPlugin() {

    companion object {
        lateinit var instance: EventsServer
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        val commandManager = PaperCommandManager(this);
        commandManager.enableUnstableAPI("help")
    }

    override fun onDisable() {

    }
}