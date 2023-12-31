package org.hyrical.events.scoreboard.adapter

import org.bukkit.entity.Player
import java.util.*

interface ScoreboardAdapter {

    fun getTitle(player: Player): String
    fun getLines(player: Player): LinkedList<String>
}