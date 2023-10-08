package org.hyrical.events.listeners

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.GameMode
import org.bukkit.entity.EntityType
import org.bukkit.entity.Firework
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
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.events.impl.CrystalFFA
import org.hyrical.events.events.impl.tnttag.TNTTag
import org.hyrical.events.listeners.customevents.EventKill
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


    fun teleport(event: PlayerTeleportEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is CrystalFFA){
            val player = event.player
            val worldBorder = player.world.worldBorder

            val to = event.to

            if (!worldBorder.isInside(to)){
                player.sendMessage(translate("&cYour pearl would have landed inside of the world border however we cancelled it. (say thanks noytorba)"))
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun join(event: PlayerJoinEvent){
        val player = event.player

        event.joinMessage = translate("&b${player.name} &f&ohas joined the server!")

        if (EventManager.spectators.contains(player.uniqueId)){
            player.gameMode = GameMode.SPECTATOR
        }
        object : BukkitRunnable(){
            override fun run() {
                player.teleport(Spawn.getSpawnLocation())
            }
        }.runTaskLater(EventsServer.instance, 5L)

    }

    @EventHandler
    fun quit(event: PlayerQuitEvent){
        val player = event.player

        event.quitMessage = null

        if (EventManager.currentEvent != null && EventManager.alivePlayers.contains(player.uniqueId)){
            EventManager.alivePlayers.remove(player.uniqueId)
            EventManager.spectators.add(player.uniqueId)

            player.inventory.clear()
        }
    }

    @EventHandler
    fun hungerEvent(event: FoodLevelChangeEvent){
        event.isCancelled = true
    }

    @EventHandler
    fun respawn(event: PlayerRespawnEvent){
        if (EventManager.currentEvent == null){
            event.player.teleport(Spawn.getSpawnLocation())
        }
    }

    @EventHandler
    fun deathEvent(event: PlayerDeathEvent){
        val player = event.player
        if (EventManager.currentEvent == null){
            player.teleport(Spawn.getSpawnLocation())

            return
        }

        if (EventManager.spectators.contains(player.uniqueId)) return

        val deathLocation = event.player.location

        EventManager.spectators.add(player.uniqueId)
        EventManager.alivePlayers.remove(player.uniqueId)

        event.deathMessage = translate(EventManager.currentEvent!!.getDeathMessage(player, player.killer))

        object : BukkitRunnable(){
            override fun run() {
                player.spigot().respawn()
                player.teleport(deathLocation)
            }
        }.runTaskLater(EventsServer.instance, 10L)

        player.gameMode = GameMode.SPECTATOR

        player.sendTitle(translate("&c&lYOU DIED!"), translate("&fYou have been killed. You are now spectating."))

        if (EventManager.alivePlayers.size == 1){
            val damager = Bukkit.getPlayer(EventManager.alivePlayers.first())!!

            Bukkit.broadcastMessage("")
            Bukkit.broadcastMessage("")
            Bukkit.broadcastMessage(translate("&a&l${damager.name} &fhas won the event!"))
            Bukkit.broadcastMessage(translate("&a&l${damager.name} &fhas won the event!"))
            Bukkit.broadcastMessage(translate("&a&l${damager.name} &fhas won the event!"))
            Bukkit.broadcastMessage("")
            Bukkit.broadcastMessage("")
            damager.sendTitle(translate("&a&lYOU WON!"), translate("&fYou won! Make a ticket for the prize."))

            BuildManager.disableBuilding()
            CombatManager.disableCombat()

            var x = 0

            if (EventManager.currentEvent is TNTTag){
                TNTTag.stopRound()
            }

            object : BukkitRunnable(){
                override fun run(){
                    val fireworksLocation = damager.location.clone()

                    for (i in 1..5) {
                        val firework = fireworksLocation.world.spawnEntity(fireworksLocation, EntityType.FIREWORK) as Firework
                        val meta = firework.fireworkMeta
                        meta.addEffect(
                            FireworkEffect.builder()
                                .withColor(Color.RED)
                                .with(FireworkEffect.Type.BALL_LARGE)
                                .flicker(true)
                                .trail(true)
                                .build())
                        meta.power = 1
                        firework.fireworkMeta = meta
                    }
                    x++

                    if (x == 5){
                        cancel()

                        EventManager.stopEvent()
                    }
                }
            }.runTaskTimer(EventsServer.instance, 0L, 15L)
        }
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