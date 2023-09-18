package org.hyrical.events.scoreboard.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.hyrical.events.scoreboard.ScoreboardHandler

object ScoreboardListener : Listener {

    @EventHandler
    fun quit(event: PlayerQuitEvent){
        ScoreboardHandler.delete(event.player)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun join(event: PlayerJoinEvent){
        ScoreboardHandler.create(event.player)
    }
}