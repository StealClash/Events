package org.hyrical.events.expansion

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player
import org.hyrical.events.events.EventManager
import org.hyrical.events.utils.convertToSmallCapsFont

class EventsExpansion : PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "event"
    }

    override fun getAuthor(): String {
        return "Embry"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun onPlaceholderRequest(player: Player, params: String): String? {
        when (params){
            "type" -> {
                return if (EventManager.currentEvent == null){
                    convertToSmallCapsFont("none")
                } else {
                    convertToSmallCapsFont(EventManager.currentEvent!!.getDisplayName())
                }
            }
            else -> {}
        }

        return null
    }
}