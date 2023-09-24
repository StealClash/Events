package org.hyrical.events.listeners

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.managers.BuildManager
import org.hyrical.events.managers.CombatManager
import org.hyrical.events.utils.Spawn
import org.hyrical.events.utils.translate
import java.awt.desktop.QuitEvent


object EventListeners : Listener {

    @EventHandler
    fun build(event: BlockPlaceEvent){
        if (!BuildManager.isBuildingEnabled() && event.player.gameMode != GameMode.CREATIVE){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun breakEvent(event: BlockBreakEvent){
        if (!BuildManager.isBuildingEnabled() && event.player.gameMode != GameMode.CREATIVE){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun hit(event: EntityDamageByEntityEvent){
        if (event.entity !is Player || event.damager !is Player) return
        if (CombatManager.isCombatEnabled()) return
        if ((event.damager as Player).gameMode == GameMode.CREATIVE) return

        event.isCancelled = true
    }

    @EventHandler
    fun join(event: PlayerJoinEvent){
        val player = event.player

        event.joinMessage = "&b${player.name} &f&ohas joined the server!"

        if (EventManager.spectators.contains(player.uniqueId)){
            player.gameMode = GameMode.SPECTATOR
        }
    }

    @EventHandler
    fun quit(event: PlayerQuitEvent){
        val player = event.player

        event.quitMessage = null

        if (EventManager.currentEvent != null && EventManager.alivePlayers.contains(player.uniqueId)){
            EventManager.alivePlayers.remove(player.uniqueId)
            EventManager.spectators.add(player.uniqueId)
        }
    }

    @EventHandler
    fun hungerEvent(event: FoodLevelChangeEvent){
        event.isCancelled = true
    }

    @EventHandler
    fun deathEvent(event: PlayerDeathEvent){
        val player = event.player
        if (EventManager.currentEvent == null){
            player.teleport(Spawn.getSpawnLocation())

            return
        }

        val deathLocation = event.player.location

        EventManager.spectators.add(player.uniqueId)
        EventManager.alivePlayers.remove(player.uniqueId)

        object : BukkitRunnable(){
            override fun run() {
                player.spigot().respawn()
            }
        }.runTaskLater(EventsServer.instance, 1L)

        player.gameMode = GameMode.SPECTATOR

        player.sendTitle(translate("&c&lYOU DIED!"), translate("&fYou have been killed. You are now spectating."))

        object : BukkitRunnable(){
            override fun run() {
                player.teleport(deathLocation)
            }
        }.runTaskLater(EventsServer.instance, 1L)
    }

    @EventHandler
    fun fallDamage(event: EntityDamageEvent) {
        if (event.entity is Player) {
            val p = event.entity as Player

            if (CombatManager.isCombatEnabled()) return

            if (event.cause === DamageCause.FALL) {
                event.isCancelled = true
            }
        }
    }
}