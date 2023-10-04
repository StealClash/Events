package org.hyrical.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Flags
import co.aikar.commands.annotation.Name
import org.bukkit.entity.Player
import org.hyrical.events.utils.translate

object TPHereCommand : BaseCommand() {

    @CommandAlias("tphere")
    @CommandPermission("event.tphere")
    fun tphere(player: Player, @Flags("other") @Name("target") target: Player){
        target.teleport(player.location)
        player.sendMessage(translate("&aTeleported player to your location."))
    }
}