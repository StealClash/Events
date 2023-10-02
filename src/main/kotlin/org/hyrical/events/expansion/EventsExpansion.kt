package org.hyrical.events.expansion

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.entity.Player
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.impl.tnttag.TNTTag
import org.hyrical.events.utils.convertToSmallCapsFont
import org.hyrical.events.utils.translate
import org.stealclash.basic.luckperms.coloredName

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
            "color" -> {
                return if (EventManager.currentEvent != null && EventManager.currentEvent is TNTTag && EventManager.alivePlayers.isNotEmpty()){
                    if (TNTTag.tntPlayers.contains(player)){
                        "&c"
                    } else {
                        "&7"
                    }
                } else {
                    translate(player.coloredName)
                }
            }
            else -> {}
        }

        return null
    }
}