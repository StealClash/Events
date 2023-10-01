package org.hyrical.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.hyrical.events.utils.translate

object FreezeMovementCommand : BaseCommand(), Listener {

    var isFrozen = false

    @CommandAlias("freezemovement")
    @CommandPermission("event.admin")
    fun freezeMovement(player: Player){
        isFrozen = !isFrozen

        player.sendMessage(translate((
                if (isFrozen) "&a" else "&c"
        ) + "You have " + if (isFrozen) "enabled" else "disabled" + " server movement."))

        if (isFrozen){
            Bukkit.broadcastMessage(translate("&cThe server has been frozen."))
        } else {
            Bukkit.broadcastMessage(translate("&aThe server has been unfrozen."))
        }
    }

    @EventHandler
    fun move(event: PlayerMoveEvent){
        if (event.from.blockX == event.to.getBlockX() && event.from.blockY == event.to.blockY && event.from.blockZ == event.to.blockZ) return
        if (!isFrozen) return

        event.to = event.from
        event.player.sendMessage(translate("&cThe server is currently frozen."))
    }

}