package org.hyrical.events.events.impl.sumo.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Subcommand
import org.bukkit.entity.Player
import org.hyrical.events.events.impl.sumo.Sumo

@CommandAlias("sumo")
@CommandPermission("event.admin")
object SumoCommand : BaseCommand() {

    @Subcommand("start")
    fun sumoCommand(player: Player){
        Sumo.startRound()
    }
}