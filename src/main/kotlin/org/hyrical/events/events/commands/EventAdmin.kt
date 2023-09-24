package org.hyrical.events.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.EventObject
import org.hyrical.events.events.gui.EventsGUI
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
    fun setTeleport(player: Player, @Single @Name("event") eventName: String, @Name("number") number: Int){

        if (number == 1){
            val eventObject = EventObject(eventName, location1 = player.location, null)

            EventManager.serializeEvent(eventObject)
        } else if (number == 2){
            val eventObject = EventObject(eventName, null, location2 = player.location)

            EventManager.serializeEvent(eventObject)
        } else {
            player.sendMessage(translate("&cYou must provide a teleport location to set."))
            return
        }

        player.sendMessage(translate("&asaved locations for $number"))
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
    fun revive(player: Player, @Name("player") target: Player){
        if (EventManager.currentEvent == null) return
        if (EventManager.alivePlayers.contains(target.uniqueId)){
            player.sendMessage(translate("&cThat player is currently alive."))
            return
        }

        EventManager.alivePlayers.add(target.uniqueId)
        EventManager.spectators.remove(target.uniqueId)

        target.gameMode = GameMode.SURVIVAL
        scatter(target, EventManager.currentEvent!!.getName())
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

                if (x < lX && z < lZ){
                    randomX = EventsServer.RANDOM.nextDouble(x, lX)
                    randomZ = EventsServer.RANDOM.nextDouble(z, lZ)
                } else {
                    randomX = EventsServer.RANDOM.nextDouble(lX, x)
                    randomZ = EventsServer.RANDOM.nextDouble(lZ, z)
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