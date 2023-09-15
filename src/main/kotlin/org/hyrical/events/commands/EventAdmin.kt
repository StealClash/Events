package org.hyrical.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Name
import co.aikar.commands.annotation.Subcommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import org.hyrical.events.combat.CombatManager
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.kits.KitsManager
import org.hyrical.events.teleport.TeleportManager
import org.hyrical.events.utils.Chat

@CommandAlias("event-admin")
@CommandPermission("events.admin")
object EventAdmin : BaseCommand() {

    @HelpCommand
    fun help(sender: CommandSender, help: CommandHelp) {
        help.showHelp()
    }

    @Subcommand("stats")
    @Description("Shows statistics about the current event.")
    fun stats(sender: CommandSender) {
        sender.sendMessage(Chat.format("&#FF5C82&lS&#FE6689&lT&#FD7091&lE&#FC7A98&lA&#FB839F&lL&#FA8DA7&lC&#F997AE&lL&#F8A1B5&lA&#F8ABBD&lS&#F7B5C4&lH &#F6BFCB&lE&#F5C9D3&lV&#F4D2DA&lE&#F3DCE1&lN&#F2E6E9&lT&#F1F0F0&lS"))
        sender.sendMessage(" ")
        sender.sendMessage(Chat.format("&eCurrent Event: &f" + EventManager.getCurrentEventName()))
        sender.sendMessage(Chat.format("&eAlive: &f" + EventManager.getCurrentAlive()))
        sender.sendMessage(Chat.format("&eSpectators: &f" + EventManager.getCurrentSpectators()))
        sender.sendMessage(Chat.format("&eHosted By: &f" + EventManager.activeEvent?.startedBy))
    }

    @Subcommand("setup")
    @Description("Shows information on how to setup an ffa event.")
    fun setup(sender: CommandSender) {
        sender.sendMessage(Chat.format("&aHow to setup events:"))
        sender.sendMessage(Chat.format("&a "))
        sender.sendMessage(Chat.format("&AStart by setting the /teleportall sides with /setteleportall"))
        sender.sendMessage(Chat.format("&aAfter that update the alive players in /event-admin reviveall"))
        sender.sendMessage(Chat.format("&aThen set the kit in /event-admin setkit"))
        sender.sendMessage(Chat.format("&aThen run /event-admin start <name> then /teleportall"))
        sender.sendMessage(Chat.format("&aThen run /event-admin giveallkit"))
        sender.sendMessage(Chat.format("&aLastly enable combat with /event-admin enablecombat and let chaos ensue."))
        sender.sendMessage(Chat.format("&7&oGood luck!"))
    }

    @Subcommand("start")
    @Description("Starts an event (Does not teleport and revive folks)")
    fun start(sender: CommandSender, @Name("eventName") name: String) {
        if (EventManager.isEventActive()) {
            sender.sendMessage(Chat.format("&cError: There is already an ongoing event."))
            return
        }

        val event = Event(
            name = name,
            startedBy = if (sender is ConsoleCommandSender) {
                "Console"
            } else {
                (sender as Player).name
            }
        )

        EventManager.setActiveEvent(event)
        sender.sendMessage(Chat.format("&aYou have activated an event."))
    }

    @Subcommand("teleportall")
    @Description("Teleports all player's to the teleport location who are current alive.")
    fun teleportAll(sender: CommandSender) {
        if (!TeleportManager.hasTeleportLocation()) {
            sender.sendMessage(Chat.format("&cError: There is no teleport location, you can set one using /event-admin setteleportall."))
            return
        }

        if (!EventManager.isEventActive()) {
            sender.sendMessage(Chat.format("&eError: There is no ongoing event."))
            return
        }

        EventManager.activeEvent?.alive?.forEach {
            Bukkit.getPlayer(it)?.teleport(TeleportManager.teleportLocation!!)
        }

        sender.sendMessage(Chat.format("&aAll player's have been successfully teleported."))
    }
    @Subcommand("setteleportall")
    @Description("Sets the location for the teleportall command")
    fun setTeleportAll(player: Player) {
        TeleportManager.teleportLocation = player.location

        player.sendMessage(Chat.format("&aSuccessfully set the teleportall location."))
    }

    @Subcommand("enablecombat")
    @Description("Enables the server combat, recommended to disable for the first 30 seconds during FFA.")
    fun enableCombat(sender: CommandSender) {
        CombatManager.enableCombat()
        Bukkit.broadcastMessage(Chat.format("&aCombat has been enabled..."))
    }

    @Subcommand("disablecombat")
    @Description("Disables the server combat, recommended to keep disabled for the first 30s econds during FFA.")
    fun disableCombat(sender: CommandSender) {
        CombatManager.disableCombat()
        Bukkit.broadcastMessage(Chat.format("&cCombat has been disabled..."))
    }

    @Subcommand("revive")
    @Description("Revives a specific player.")
    fun revive(sender: Player, @Name("target") target: Player) {
        if (!EventManager.isEventActive()) {
            sender.sendMessage(Chat.format("&cError: There is no ongoing event."))
            return
        }

        if (EventManager.activeEvent?.alive?.contains(target.uniqueId) == true) {
            sender.sendMessage(Chat.format("&cError: The target player is currently alive."))
            return
        }

        target.teleport(target, PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT)
        KitsManager.applyKit(target)

        EventManager.activeEvent?.alive?.add(target.uniqueId)
        EventManager.activeEvent?.spectating?.remove(target.uniqueId)

        sender.sendMessage(Chat.format("&aYou have revived ${target.name}."))
    }
    @Subcommand("reviveall")
    @Description("Revives all player's.")
}