package org.hyrical.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.command.CommandSender
import org.hyrical.events.events.EventManager
import org.hyrical.events.utils.Chat

@CommandAlias("event-admin")
@CommandPermission("events.admin")
object EventAdmin : BaseCommand() {

    @HelpCommand
    fun help(sender: CommandSender, help: CommandHelp) {
        help.showHelp()
    }

    @Subcommand("stats")
    fun stats(sender: CommandSender) {
        sender.sendMessage(Chat.format("&#FF5C82&lS&#FE6689&lT&#FD7091&lE&#FC7A98&lA&#FB839F&lL&#FA8DA7&lC&#F997AE&lL&#F8A1B5&lA&#F8ABBD&lS&#F7B5C4&lH &#F6BFCB&lE&#F5C9D3&lV&#F4D2DA&lE&#F3DCE1&lN&#F2E6E9&lT&#F1F0F0&lS"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&eCurrent Event: &f" + EventManager.getCurrentEventName()))
        sender.sendMessage(Chat.format("&eAlive: &f" + EventManager.getCurrentAlive()))
        sender.sendMessage(Chat.format("&eSpectators: &f" + EventManager.getCurrentSpectators()))
        sender.sendMessage(Chat.format("&eHosted By: &f" + EventManager.activeEvent?.startedBy))
    }

    @Subcommand("start")
    fun start(sender: CommandSender, @Name("eventName") name: String) {
        
    }
}