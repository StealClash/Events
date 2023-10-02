package org.hyrical.events.events

import co.aikar.commands.PaperCommandManager
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.potion.PotionEffectType
import org.hyrical.events.EventsServer
import org.hyrical.events.events.impl.CrystalFFA
import org.hyrical.events.events.impl.SimonSays
import org.hyrical.events.events.impl.spleef.Spleef
import org.hyrical.events.events.impl.tnttag.TNTTag
import org.hyrical.events.utils.Spawn
import org.hyrical.events.utils.saveToConfig
import java.util.UUID

object EventManager {

    val alivePlayers: ArrayList<UUID> = arrayListOf()
    val spectators: ArrayList<UUID> = arrayListOf()

    val events: ArrayList<Event> = arrayListOf()

    var currentEvent: Event? = null

    var baseTime: Long = 0L
    var timeLeft: Long = 0L

    fun init(acf: PaperCommandManager){
        events.add(CrystalFFA)
        events.add(SimonSays)
        events.add(TNTTag)
        events.add(Spleef)

        for (event in events){
            for (command in event.getCommands()){
                acf.registerCommand(command)
            }

            for (listener in event.getListeners()){
                Bukkit.getPluginManager().registerEvents(listener, EventsServer.instance)
            }
        }
    }

    fun serializeEvent(obj: EventObject){
        if (obj.location1 != null){
            saveToConfig("${obj.name}.location1.x", obj.location1!!.x)
            saveToConfig("${obj.name}.location1.z", obj.location1!!.z)

            saveToConfig("${obj.name}.world", obj.location1!!.world.name)
        }

        if (obj.location2 != null){
            saveToConfig("${obj.name}.location2.x", obj.location2!!.x)
            saveToConfig("${obj.name}.location2.z", obj.location2!!.z)
        }

        EventsServer.instance.saveConfig()
    }

    fun updateAlivePlayers(){
        alivePlayers.clear()
        spectators.clear()

        for (player in Bukkit.getOnlinePlayers()){
            if (player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) continue

            alivePlayers.add(player.uniqueId)
        }

        println("Updated alive players")
    }

    fun getEvent(name: String): Event {
        return events.first { it.getName() == name }
    }

    fun stopEvent(){
        currentEvent?.endEvent()
        currentEvent = null
        timeLeft = 0L
        baseTime = 0L
        alivePlayers.clear()
        spectators.clear()

        for (p in Bukkit.getOnlinePlayers()){
            p.teleport(Spawn.getSpawnLocation())
            p.gameMode = GameMode.SURVIVAL

            p.inventory.clear()
            p.removePotionEffect(PotionEffectType.SPEED)
        }
    }
}