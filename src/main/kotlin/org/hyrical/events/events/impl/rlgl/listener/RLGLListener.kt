package org.hyrical.events.events.impl.rlgl.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.rlgl.RLGL
import org.hyrical.events.events.impl.rlgl.RLGLStatus
import org.hyrical.events.utils.translate

object RLGLListener : Listener {
   @EventHandler
    fun move(event: PlayerMoveEvent){
        if (event.from.blockX == event.to.blockX && event.from.blockZ == event.to.blockZ) return
        if (EventManager.currentEvent != RLGL::class.java) return
        if (RLGL.currentStatus != RLGLStatus.STOP || RLGL.currentStatus != RLGLStatus.WAITING) return

       val player = event.player

       player.health = 0.0
    }

}