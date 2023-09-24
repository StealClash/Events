package org.hyrical.events.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.hyrical.events.EventsServer

object Spawn {

    val config = EventsServer.instance.config

    fun getSpawnLocation(): Location {
        val world = Bukkit.getWorld(config.getString("spawn.world")!!)
        val x = config.getDouble("spawn.x")
        val y = config.getDouble("spawn.y")
        val z = config.getDouble("spawn.z")
        val pitch = config.getDouble("spawn.pitch").toFloat()
        val yaw = config.getDouble("spawn.yaw").toFloat()

        return Location(world, x, y, z, yaw, pitch)
    }

    fun setSpawnLocation(location: Location){
        val world = location.world.name
        val x = location.x
        val y = location.y
        val z = location.z
        val pitch = location.pitch
        val yaw = location.yaw

        config.set("spawn.world", world)
        config.set("spawn.x", x)
        config.set("spawn.y", y)
        config.set("spawn.z", z)
        config.set("spawn.pitch", pitch)
        config.set("spawn.yaw", yaw)

        EventsServer.instance.saveConfig()
    }
}