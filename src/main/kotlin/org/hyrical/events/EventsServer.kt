package org.hyrical.events

import co.aikar.commands.PaperCommandManager
import org.bukkit.plugin.java.JavaPlugin
import org.hyrical.events.kits.KitsManager
import kotlin.random.Random

class EventsServer : JavaPlugin() {

    companion object {
        lateinit var instance: EventsServer
        var RANDOM = Random(System.currentTimeMillis())
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