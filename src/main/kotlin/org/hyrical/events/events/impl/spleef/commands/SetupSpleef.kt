package org.hyrical.events.events.impl.spleef.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.hyrical.events.utils.translate

class SetupSpleef : BaseCommand() {

    val world = Bukkit.getWorld("spleef_world")

    @CommandAlias("setupspleef")
    @CommandPermission("event.admin")
    fun setupSpleef(player: Player){
        teleportPlayer(player, 65)
        teleportPlayer(player, 73)
        teleportPlayer(player, 81)
        teleportPlayer(player, 89)

        player.sendMessage(translate("&aDone cuh"))
    }

    private fun teleport(player: Player, x: Int, y: Int, z: Int){
        player.teleport(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
    }

    private fun teleportPlayer(player: Player, y: Int){
        teleport(player, 39, y ,36)
        player.performCommand("/pos1")
        teleport(player, -22, y ,-28)
        player.performCommand("/pos2")
        player.performCommand("/set snow_block")
    }
}