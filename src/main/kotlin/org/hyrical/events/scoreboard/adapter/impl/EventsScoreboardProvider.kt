package org.hyrical.events.scoreboard.adapter.impl

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.hyrical.events.events.EventManager
import org.hyrical.events.managers.BuildManager
import org.hyrical.events.managers.CombatManager
import org.hyrical.events.scoreboard.adapter.ScoreboardAdapter
import org.hyrical.events.utils.convertToSmallCapsFont
import org.hyrical.events.utils.translate
import org.hyrical.hcf.utils.time.TimeUtils
import java.util.*

class EventsScoreboardProvider : ScoreboardAdapter {
    override fun getTitle(player: Player): String {
        return "&#FB1D1D&lEVENTS"
    }

    override fun getLines(player: Player): LinkedList<String> {
        val lines: LinkedList<String> = LinkedList()

        lines.add("")
        if (EventManager.currentEvent != null){
            lines.add(" &#FBC504⛃ &fEvent: &#FBC504${convertToSmallCapsFont(EventManager.currentEvent.getName())}")
        } else {
            lines.add(" &#FBC504⛃ &fEvent: &#FBC504None!")
        }
        if (EventManager.time == 0L){
            lines.add(" &#FBC504\uD83C\uDF0A &#FBC5040s")
        } else {
            lines.add(" &#FBC504\uD83C\uDF0A &#FBC504${TimeUtils.formatDuration(EventManager.time - System.currentTimeMillis())}")
        }
        lines.add("")
        lines.add(" &#7CB3FB\uD83C\uDFA3 &fAlive: &#7CB3FB${EventManager.alivePlayers.size}/${Bukkit.getOnlinePlayers().size}}")
        lines.add(" &#ff0021\uD83D\uDDE1 &fPvP&f: &#ff0021${convertToSmallCapsFont(CombatManager.isCombatEnabled().toString())}")
        lines.add(" &#DD7305☠ &fBuild&f: &#DD7305${convertToSmallCapsFont(BuildManager.isBuildingEnabled().toString())}")

        if (EventManager.currentEvent != null && EventManager.currentEvent.getScoreboardLines().isNotEmpty()){
            val eventSBLines = EventManager.currentEvent.getScoreboardLines()

            for (line in eventSBLines){
                lines.add(line)
            }
        }

        lines.add("")
        lines.add("&7&oplay.stealclash.org")

        return lines
    }

    fun
}