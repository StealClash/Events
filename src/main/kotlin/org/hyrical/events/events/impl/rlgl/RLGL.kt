package org.hyrical.events.events.impl.rlgl

import co.aikar.commands.BaseCommand
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.utils.translate
import java.util.UUID

object RLGL : Event() {

    var currentStatus: RLGLStatus = RLGLStatus.WAITING
    var task: BukkitTask? = null

    var mainGameTimer = 0

    val completedPlayers: ArrayList<UUID> = arrayListOf()
    val stillRunningPlayers: ArrayList<UUID> = arrayListOf()

    var gameLoopTask: BukkitTask? = null

    override fun getName(): String {
        return "RLGL"
    }

    override fun getDisplayName(): String {
        return "RLGL (not implemented)"
    }

    override fun getScoreboardLines(): MutableList<String> {
        return mutableListOf("", "Status: ${currentStatus.display}")
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf()
    }

    override fun startEvent() {
        mainGameTimer = 120

        gameLoopTask = object : BukkitRunnable() {
            override fun run() {
                if (mainGameTimer == 0){
                    for (uncompleted in stillRunningPlayers){
                        val player = Bukkit.getPlayer(uncompleted) ?: continue

                        player.health = 0.0
                        player.sendMessage(translate("&cYou did not make it in time!"))
                    }

                    for (completed in completedPlayers){
                        val player = Bukkit.getPlayer(completed) ?: continue

                        EventAdmin.scatter(player, getName())
                    }
                }

                if (mainGameTimer != 0){
                    mainGameTimer--
                }
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }
}