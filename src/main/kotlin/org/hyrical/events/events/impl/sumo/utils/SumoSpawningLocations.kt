package org.hyrical.events.events.impl.sumo.utils

import co.aikar.commands.BaseCommand
import org.bukkit.Location
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
            config.set("Sumo.platform.world", obj.location1!!.world)
        }

        if (obj.location2 != null){
            config.set("Sumo.platform.location1.x", obj.location1!!.x)
            config.set("Sumo.platform.location1.y", obj.location1!!.y)
            config.set("Sumo.platform.location1.z", obj.location1!!.z)
            config.set("Sumo.platform.world", obj.location1!!.world)
        }
    }

}