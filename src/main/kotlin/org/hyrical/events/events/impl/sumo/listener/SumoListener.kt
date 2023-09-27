package org.hyrical.events.events.impl.sumo.listener

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.sumo.Sumo
import org.hyrical.events.listeners.customevents.EventKill

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
    fun waterEvent(event: PlayerMoveEvent){
        val player: Player = event.player
        val location: Location = player.location

        if (player.gameMode == GameMode.SPECTATOR || player.gameMode == GameMode.CREATIVE || EventManager.spectators.contains(player.uniqueId)) return
        if (Sumo.playersPicked!!.first != player || Sumo.playersPicked!!.second != player) return


    }

}