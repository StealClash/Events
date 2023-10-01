package org.hyrical.events.events.impl.sumo.listener

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.EnderPearl
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.sumo.Sumo


object SumoListener : Listener {

    @EventHandler
    fun deathEvent(event: PlayerDeathEvent){
        val victim = event.player

        if (EventManager.currentEvent != Sumo::class.java) return
        if (Sumo.playersPicked == null) return
        if (Sumo.playersPicked!!.first != victim || Sumo.playersPicked!!.second != victim) return

        Sumo.cleanUpRound()
    }

    @EventHandler
    fun move(event: PlayerMoveEvent){
        if (event.from.blockX == event.to.blockX && event.from.blockZ == event.to.blockZ) return
        if (EventManager.currentEvent != Sumo::class.java) return
        if (Sumo.canMove) return

        val player = event.player

        if (Sumo.playersPicked!!.first == player || Sumo.playersPicked!!.second == player){
            event.to = event.from
        }
    }

    @EventHandler
    fun waterEvent(event: PlayerMoveEvent){
        val player: Player = event.player
        val location: Location = player.location

        if (player.gameMode == GameMode.SPECTATOR || player.gameMode == GameMode.CREATIVE || EventManager.spectators.contains(player.uniqueId)) return
        if (Sumo.playersPicked == null) return
        if (Sumo.playersPicked!!.first != player || Sumo.playersPicked!!.second != player) return

        player.health = 0.0
    }

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        val projectile = event.entity
        if (projectile is EnderPearl) {
            val player = projectile.getShooter() as Player?
            val pearlLocation = projectile.getLocation()
            val worldBorder = player!!.world.worldBorder
            val borderSize = worldBorder.size / 2 // Get the radius of the border
            if (pearlLocation.distanceSquared(worldBorder.center) > borderSize * borderSize) {
                // The player's enderpearl landed outside the border
                // Handle accordingly (e.g., damage the player, teleport them back, etc.)
            }
        }
    }

}