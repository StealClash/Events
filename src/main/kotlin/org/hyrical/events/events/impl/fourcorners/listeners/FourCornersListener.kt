package org.hyrical.events.events.impl.fourcorners.listeners

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.fourcorners.FourCorners
import org.hyrical.events.events.impl.spleef.Spleef
import org.hyrical.events.utils.translate

class FourCornersListener : Listener {

    @EventHandler
    fun waterEvent(event: PlayerMoveEvent){
        val player: Player = event.player
        val location: Location = player.location
        val world = Bukkit.getWorld("fourcorners")!!

        if (player.gameMode == GameMode.SPECTATOR || player.gameMode == GameMode.CREATIVE || EventManager.spectators.contains(player.uniqueId)) return
        if (EventManager.currentEvent == null || EventManager.currentEvent !is FourCorners) return

        world.strikeLightningEffect(player.location)
        player.health = 0.0
    }

    @EventHandler
    fun death(event: PlayerDeathEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is Spleef){
            event.deathMessage = translate("&b${event.player.name} &fhas died.")
        }
    }
}