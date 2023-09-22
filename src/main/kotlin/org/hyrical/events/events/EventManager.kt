package org.hyrical.events.events

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.hyrical.events.events.impl.TestEvent
import org.hyrical.events.utils.saveToConfig
import java.util.UUID

object EventManager {

    val alivePlayers: ArrayList<UUID> = arrayListOf()
    val spectators: ArrayList<UUID> = arrayListOf()

    val events: ArrayList<Event> = arrayListOf()

    var eventHost: Player? = null
    var eventStartedAt: Long = 0L
    var currentEvent: Event? = null

    var baseTime: Long = 0L
    var timeLeft: Long = 0L

    fun init(){
        events.add(TestEvent)
    }

    fun serializeEvent(obj: EventObject){
        if (obj.location1 != null){
            saveToConfig("${obj.name}.location1.x", obj.location1!!.x)
            saveToConfig("${obj.name}.location1.z", obj.location1!!.z)

            saveToConfig("${obj.name}.world", obj.location1!!.world)
        }

        if (obj.location2 != null){
            saveToConfig("${obj.name}.location2.x", obj.location2!!.x)
            saveToConfig("${obj.name}.location2.z", obj.location2!!.z)
        }

    }

    fun updateAlivePlayers(){
        alivePlayers.clear()

        for (player in Bukkit.getOnlinePlayers()){
            alivePlayers.add(player.uniqueId)
        }

        println("Updated alive players")
    }

    fun getEvent(name: String): Event {
        return events.first { it.getName() == name }
    }

    fun getEventObject(){

    }
}