package org.hyrical.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import org.bukkit.entity.Player
import org.hyrical.events.events.EventManager
import org.hyrical.events.utils.Spawn
import org.hyrical.events.utils.translate

object SpawnCommand : BaseCommand() {

    @CommandAlias("spawn")
    fun spawn(player: Player){
        if (EventManager.currentEvent == null){
            player.teleport(Spawn.getSpawnLocation())
        } else if (!EventManager.alivePlayers.contains(player.uniqueId)){
            player.teleport(Spawn.getSpawnLocation())
        } else {
            player.sendMessage(translate("&cYou can't /spawn right now!"))
        }
    }

    @CommandAlias("setspawn")
    @CommandPermission("core.setspawn")
    fun setspawn(player: Player){
        Spawn.setSpawnLocation(player.location)

        player.sendMessage(translate("&aSet spawn to your location"))
    }
}