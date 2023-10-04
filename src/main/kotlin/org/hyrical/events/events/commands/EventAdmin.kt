package org.hyrical.events.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.EventObject
import org.hyrical.events.events.gui.EventsGUI
import org.hyrical.events.events.impl.tnttag.TNTTag
import org.hyrical.events.utils.Spawn
import org.hyrical.events.utils.translate

@CommandAlias("event-admin")
@CommandPermission("events.admin")
object EventAdmin : BaseCommand() {

    val config = EventsServer.instance.config

    @Default
    fun eventGUI(player: Player){
        EventsGUI().openMenu(player)
    }

    @Subcommand("setteleportlocations")
    fun setTeleport(player: Player, @Single @Name("event") eventName: String, @Name("number") number: Int) {

        if (number == 1) {
            val eventObject = EventObject(eventName, location1 = player.location, null)

            EventManager.serializeEvent(eventObject)
        } else if (number == 2) {
            val eventObject = EventObject(eventName, null, location2 = player.location)

            EventManager.serializeEvent(eventObject)
        } else {
            player.sendMessage(translate("&cYou must provide a teleport location to set."))
            return
        }

        player.sendMessage(translate("&asaved locations for $number"))
    }

    @Subcommand("stop")
    fun stopEvent(player: Player){
        if (EventManager.currentEvent == null) return

        if (EventManager.currentEvent is TNTTag){
            TNTTag.task1?.cancel()
            TNTTag.task1 = null
        }

        EventManager.stopEvent()

        Bukkit.broadcastMessage("")
        Bukkit.broadcastMessage(translate("&cThe event has been forcefully stopped!"))
        Bukkit.broadcastMessage("")
    }

    @Subcommand("alive")
    fun alive(player: Player){
        player.sendMessage("")
        player.sendMessage(translate("&aAlive Players &7(${EventManager.alivePlayers.size}):"))
        player.sendMessage("")
        for (alive in EventManager.alivePlayers){
            player.sendMessage(translate("&f- &a${Bukkit.getPlayer(alive)?.name}"))
        }
        player.sendMessage("")
    }

    @Subcommand("dead")
    fun dead(player: Player){
        player.sendMessage("")
        player.sendMessage(translate("&cDead Players &7(${EventManager.spectators.size}):"))
        player.sendMessage("")
        for (alive in EventManager.spectators){
            player.sendMessage(translate("&f- &c${Bukkit.getPlayer(alive)?.name}"))
        }
        player.sendMessage("")
    }

    @Subcommand("scatter")
    fun eventScatter(player: Player, @Single @Name("event") eventName: String, @Name("player") inputString: String){
        if (inputString == "all"){
            for (p in Bukkit.getOnlinePlayers()){
                scatter(p, eventName)
            }
        } else {
            val target = Bukkit.getPlayer(inputString) ?: return

            scatter(target, eventName)
        }

        player.sendMessage(translate("&aScattered player(s)."))
    }

    @Subcommand("revive")
    fun revive(player: Player, @Flags("other") @Name("player") target: Player){
        if (EventManager.currentEvent == null) return
        if (EventManager.alivePlayers.contains(target.uniqueId)){
            player.sendMessage(translate("&cThat player is currently alive."))
            return
        }

        EventManager.alivePlayers.add(target.uniqueId)
        EventManager.spectators.remove(target.uniqueId)

        target.gameMode = GameMode.SURVIVAL
        scatter(target, EventManager.currentEvent!!.getName())
        player.sendMessage(translate("&aRevived that player."))

        EventManager.currentEvent!!.revive(target)
    }

    @Subcommand("tpalive")
    fun tpalive(player: Player, @Name("player") target: Player){
        if (EventManager.currentEvent == null) return

        for (alive in EventManager.alivePlayers){
            val alivePlayer = Bukkit.getPlayer(alive) ?: continue

            alivePlayer.teleport(player.location)
        }

        player.sendMessage(translate("&aTeleported all alive players to you."))
    }

    fun scatter(p: Player, eventName: String){
        if (config.getString("$eventName.location1") != null){
            val x = config.getDouble("$eventName.location1.x")
            val z = config.getDouble("$eventName.location1.z")


            if (config.getString("$eventName.location2") != null){
                val lX = config.getDouble("$eventName.location2.x")
                val lZ = config.getDouble("$eventName.location2.z")

                var randomX = 0.0
                var randomZ = 0.0

                randomX = if (x < lX){
                    EventsServer.RANDOM.nextDouble(x, lX)
                } else {
                    EventsServer.RANDOM.nextDouble(lX, x)

                }

                randomZ = if (z < lZ){
                    EventsServer.RANDOM.nextDouble(z, lZ)
                } else {
                    EventsServer.RANDOM.nextDouble(lZ, z)
                }


                val block = Bukkit.getWorld(config.getString("$eventName.world")!!)!!.getHighestBlockAt(randomX.toInt(), randomZ.toInt())!!
                val location = Location(block.world, block.x.toDouble(), block.y + 1.0, block.z.toDouble())

                p.teleport(location)
            } else {
                p.teleport(Location(
                    Bukkit.getWorld(config.getString("$eventName.world")!!),
                    x, Bukkit.getWorld(config.getString("$eventName.world")!!)!!.getHighestBlockAt(x.toInt(), z.toInt()).location.y + 1.0,
                    z))
            }
        }
    }
}