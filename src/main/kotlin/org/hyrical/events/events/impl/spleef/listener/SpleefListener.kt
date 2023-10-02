package org.hyrical.events.events.impl.spleef.listener

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.spleef.Spleef
import org.hyrical.events.utils.translate

class SpleefListener : Listener {

    @EventHandler
    fun blockBreak(event: BlockBreakEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is Spleef){
            for (drop in event.block.drops){
                event.player.inventory.addItem(drop)
            }
            event.block.drops.clear()
        }
    }

    @EventHandler
    fun projectileHitBlock(event: ProjectileHitEvent) {
        val entity = event.entity

        if (entity.type == EntityType.SNOWBALL) {
            event.hitBlock?.breakNaturally()
        }
    }

    @EventHandler
    fun placeBlock(event: BlockPlaceEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is Spleef){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun playerMoveEvent(event: PlayerMoveEvent){
        if (event.player.location.y <= 45){
            if (EventManager.currentEvent == null) return
            if (EventManager.currentEvent !is Spleef) return

            event.player.health = 0.0
        }
    }

    @EventHandler
    fun death(event: PlayerDeathEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is Spleef){
            event.deathMessage = translate("&b${event.player.name} &7has been spleefed.")
        }
    }
}