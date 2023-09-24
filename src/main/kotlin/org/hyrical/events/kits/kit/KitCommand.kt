package org.hyrical.events.kits.kit

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Single
import co.aikar.commands.annotation.Subcommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.hyrical.events.kits.KitsManager
import org.hyrical.events.utils.translate

@CommandAlias("kit")
@CommandPermission("events.admin")
object KitCommand : BaseCommand() {
    /*

    @Subcommand("create")
    fun create(player: Player, @Name("kit") kit: String){
        KitsManager.createKit(kit)
        player.sendMessage(translate("created kit"))
    }

    @Subcommand("delete")
    fun delete(player: Player, @Name("kit") kit: String){
        if (!KitsManager.getAllKits().contains(kit)) {
            player.sendMessage(translate("&cThat kit isn't a thing bucko"))
            return
        }

        KitsManager.deleteKit(kit)
        player.sendMessage(translate("&aDeleted the kit &f$kit"))
    }

    @Subcommand("giveall")
    fun giveKit(player: Player, @Single @Name("kit") kit: String){
        if (!KitsManager.getAllKits().contains(kit)) {
            player.sendMessage(translate("&cThat kit isn't a thing bucko"))
            return
        }

        for (p in Bukkit.getOnlinePlayers()){
            for (item in KitsManager.getItems(kit)){
                p.inventory.addItem(item)
            }
        }

        player.sendMessage(translate("&aGiven kit to everyone."))
    }

    @Subcommand("setitems")
    fun setItems(player: Player, @Name("kit") kit: String){
        KitsManager.saveItems(kit, player.inventory.contents.filterNotNull().toMutableList())
        player.sendMessage(translate("&aContents saved."))
    }

     */

}