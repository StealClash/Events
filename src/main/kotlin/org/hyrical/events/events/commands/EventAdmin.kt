package org.hyrical.events.events.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
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
    fun setTeleport(player: Player, @Name("event") eventName: String, @Name("number") number: Int){

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
    fun eventScatter(player: Player, @Name("event") eventName: String, @Optional target: Player?){
        val event = EventManager.getEvent(eventName)

        if (target == null){
            for (p in Bukkit.getOnlinePlayers()){
                scatter(p, eventName)
            }
        } else {
            scatter(target, eventName)
        }

        player.sendMessage(translate("&aScattered player(s)."))
    }

    fun scatter(p: Player, eventName: String){
        if (config.getString("$eventName.location1") != null){
            val x = config.getDouble("$eventName.location1.x")
            val z = config.getDouble("$eventName.location1.z")


            if (config.getString("$eventName.location2") != null){
                val lX = config.getDouble("$eventName.location1.x")
                val lZ = config.getDouble("$eventName.location1.z")

                val randomX = EventsServer.RANDOM.nextDouble(x, lX)
                val randomZ = EventsServer.RANDOM.nextDouble(z, lZ)

                val block = Bukkit.getWorld(config.getString("$eventName.world")!!)!!.getHighestBlockAt(randomX.toInt(), randomZ.toInt())!!

                p.teleport(block.location)
            } else {
                p.teleport(Location(
                    Bukkit.getWorld(config.getString("$eventName.world")!!),
                    x, Bukkit.getWorld(config.getString("$eventName.world")!!)!!.getHighestBlockAt(x.toInt(), z.toInt()).location.y,
                    z))
            }
        }
    }
}