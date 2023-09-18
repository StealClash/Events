package org.hyrical.events.scoreboard

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.hyrical.events.EventsServer
import org.hyrical.events.scoreboard.adapter.ScoreboardAdapter
import org.hyrical.events.scoreboard.adapter.impl.EventsScoreboardProvider
import org.hyrical.fastboard.FastBoard
import org.hyrical.events.scoreboard.listeners.ScoreboardListener
import org.hyrical.events.scoreboard.thread.ScoreboardThread
import java.util.UUID

object ScoreboardHandler {

    fun load(){
        ScoreboardThread().start()
        Bukkit.getPluginManager().registerEvents(ScoreboardListener, EventsServer.instance)
        EventsServer.instance.logger.info("[Scoreboard] Scoreboard loaded succesfully")
    }

    val boards = mutableMapOf<UUID, EventsScoreboard>()
    val adapter: ScoreboardAdapter = EventsScoreboardProvider()

    fun create(player: Player){
        boards[player.uniqueId] = EventsScoreboard(player, FastBoard(player))
    }

    fun delete(player: Player){
        boards.remove(player.uniqueId)
    }
}