package org.hyrical.events.events.impl.sumo

import co.aikar.commands.BaseCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.events.impl.sumo.listener.SumoListener
import org.hyrical.events.events.impl.sumo.utils.SumoSpawningLocations
import org.hyrical.events.utils.translate

object Sumo : Event() {

    var playersPicked: Pair<Player, Player>? = null
    var canMove = false

    override fun getName(): String {
        return "Sumo"
    }

    override fun getDisplayName(): String {
        return "Sumo"
    }

    override fun getScoreboardLines(): MutableList<String> {
        if (playersPicked == null) return mutableListOf("", "&fNo one is fighting yet.")

        return mutableListOf("",
            "&b${playersPicked!!.first.name} &fvs &b${playersPicked!!.second.name}"
        )
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf(
            SumoListener
        )
    }

    fun startRound(){
        val randomPlayers = pickRandomPlayers()

        val player1 = randomPlayers.first
        val player2 = randomPlayers.second

        playersPicked = randomPlayers

        player1.teleport(SumoSpawningLocations.getPlatformLocation().first)
        player2.teleport(SumoSpawningLocations.getPlatformLocation().second)

        canMove = false

        var i = 3

        object : BukkitRunnable(){
            override fun run() {
                if (i == 0){
                    canMove = true
                    cancel()
                    return
                }

                player1.sendTitle(translate("&fStarting in &a&l${i}&f."), translate(""))
                player2.sendTitle(translate("&fStarting in &a&l${i}&f."), translate(""))

                i--;
            }
        }.runTaskTimer(EventsServer.instance, 20L, 20L)
    }

    fun pickRandomPlayers(): Pair<Player, Player>{
        val choosePlayers: ArrayList<Player> = arrayListOf()
        for (alive in EventManager.alivePlayers){
            choosePlayers.add(Bukkit.getPlayer(alive)!!)
        }

        val firstPlayer = choosePlayers[EventsServer.RANDOM.nextInt(0, choosePlayers.size)]
        choosePlayers.remove(firstPlayer)
        val secondPlayer = choosePlayers[EventsServer.RANDOM.nextInt(0, choosePlayers.size)]

        return Pair(firstPlayer, secondPlayer)
    }

    fun cleanUpRound(){
        if (playersPicked == null) return

        val firstPlayer = playersPicked!!.first
        val secondPlayer = playersPicked!!.second

        EventAdmin.scatter(firstPlayer, "Sumo")
        EventAdmin.scatter(secondPlayer, "Sumo")

        playersPicked = null
    }


}