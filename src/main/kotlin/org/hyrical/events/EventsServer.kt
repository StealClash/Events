package org.hyrical.events

import co.aikar.commands.PaperCommandManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.kits.KitsManager
import org.hyrical.events.kits.kit.KitCommand
import org.hyrical.events.listeners.EventListeners
import org.hyrical.events.scoreboard.ScoreboardHandler
import org.hyrical.events.utils.menus.listener.MenuListener
import kotlin.random.Random

class EventsServer : JavaPlugin() {

    companion object {
        lateinit var instance: EventsServer
        var RANDOM = java.util.Random()
    }

    override fun onEnable() {
        instance = this
        saveDefaultConfig()

        val commandManager = PaperCommandManager(this)
        commandManager.enableUnstableAPI("help")

        EventManager.init(commandManager)

        commandManager.registerCommand(EventAdmin)

        registerListener(MenuListener)
        registerListener(EventListeners)

        ScoreboardHandler.load()
    }

    override fun onDisable() {
        saveConfig()
    }

    private fun registerListener(listener: Listener){
        Bukkit.getPluginManager().registerEvents(listener, this)
    }
}