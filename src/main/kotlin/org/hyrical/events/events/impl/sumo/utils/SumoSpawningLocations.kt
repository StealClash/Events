package org.hyrical.events.events.impl.sumo.utils

import co.aikar.commands.BaseCommand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.hyrical.events.EventsServer

object SumoSpawningLocations {

    val config = EventsServer.instance.config

    fun setSumoPlatformLocation(player: Player, obj: SumoLocationObject, number: Int){
        if (obj.location1 != null){
            config.set("Sumo.platform.location1.x", obj.location1!!.x)
            config.set("Sumo.platform.location1.y", obj.location1!!.y)
            config.set("Sumo.platform.location1.z", obj.location1!!.z)
            config.set("Sumo.platform.world", obj.location1!!.world.name)
        }

        if (obj.location2 != null){
            config.set("Sumo.platform.location1.x", obj.location2!!.x)
            config.set("Sumo.platform.location1.y", obj.location2!!.y)
            config.set("Sumo.platform.location1.z", obj.location2!!.z)
        }
    }

    fun getPlatformLocation(): Pair<Location, Location> {
        val world = Bukkit.getWorld(config.getString("Sumo.platform.world")!!)

        val location1 = Location(world,
            config.getDouble("Sumo.platform.location1.x"),
            config.getDouble("Sumo.platform.location1.y"),
            config.getDouble("Sumo.platform.location1.z"))
        val location2 = Location(world,
            config.getDouble("Sumo.platform.location2.x"),
            config.getDouble("Sumo.platform.location2.y"),
            config.getDouble("Sumo.platform.location2.z"))

        return Pair(location1, location2)
    }

}