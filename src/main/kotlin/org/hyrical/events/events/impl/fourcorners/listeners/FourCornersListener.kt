package org.hyrical.events.events.impl.fourcorners.listeners

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
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
        if (event.from.blockX == event.to.getBlockX() && event.from.blockY == event.to.blockY && event.from.blockZ == event.to.blockZ) return
        if (!isPlayerInWater(player)) return

        world.strikeLightningEffect(player.location)
        player.health = 0.0
    }

    private fun isPlayerInWater(player: Player): Boolean {
        val x = player.location.x
        val y = player.location.y
        val z = player.location.z

        val blockMaterial = player.world.getBlockAt(x.toInt(), y.toInt(), z.toInt()).type

        return blockMaterial == Material.WATER
    }
}