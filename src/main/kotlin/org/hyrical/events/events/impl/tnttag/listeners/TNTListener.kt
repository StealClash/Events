package org.hyrical.events.events.impl.tnttag.listeners

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.type.TNT
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.tnttag.TNTTag
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.translate

class TNTListener : Listener {

    @EventHandler
    fun damage(event: EntityDamageByEntityEvent){
        if (event.damager !is Player && event.entity !is Player) return
        if (EventManager.currentEvent !is TNTTag) return

        event.damage = 0.0

        val damager = event.damager as Player
        val victim = event.entity as Player

        if (!TNTTag.tntPlayers.contains(damager)) return
        if (damager.itemInHand != ItemBuilder.of(Material.TNT).enchant(Enchantment.DAMAGE_ALL, 1).build()) return
        if (TNTTag.tntPlayers.contains(damager) && TNTTag.tntPlayers.contains(victim)) {
            event.isCancelled = true
            return
        }

        TNTTag.tntPlayers.remove(damager)
        TNTTag.tntPlayers.add(victim)

        TNTTag.applyTNT(victim)
        TNTTag.applyRunner(damager)

        Bukkit.broadcastMessage(translate("&b${victim.name} &7is IT!"))
    }

    @EventHandler
    fun damage(event: EntityDamageEvent){
        if (event.entity !is Player) return

        if (event.cause == DamageCause.VOID){
            if (EventManager.currentEvent == null) return
            if (EventManager.currentEvent !is TNTTag) return

            val player = event.entity as Player

            event.isCancelled = true
            player.teleport(Location(player.world, 0.5,-35.5,0.5,0.0f,0.0f))
        }
    }

    @EventHandler
    fun inventoryMove(event: InventoryClickEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is TNTTag){
            val clickedInventory = event.clickedInventory

            if (clickedInventory != null && clickedInventory.equals(event.whoClicked.getInventory())) {

                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun dropEvent(event: PlayerDropItemEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is TNTTag){
            event.isCancelled = true
        }
    }

    @EventHandler
    fun deathEvent(event: PlayerDeathEvent){
        if (EventManager.currentEvent != null && EventManager.currentEvent is TNTTag){
            event.drops.clear()
        }
    }
}