package org.hyrical.events

import co.aikar.commands.PaperCommandManager
import org.bukkit.plugin.java.JavaPlugin
import org.hyrical.events.kits.KitsManager

class EventsServer : JavaPlugin() {

    companion object {
        lateinit var instance: EventsServer
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        val commandManager = PaperCommandManager(this);
        commandManager.enableUnstableAPI("help")

        KitsManager.deserializeFromConfig()
    }

    override fun onDisable() {

    }
}