package org.hyrical.events.utils

import org.bukkit.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern

object Chat {

    @JvmStatic
    fun format(message: String): String {
        val HEX_PATTERN: Pattern = Pattern.compile("&#([0-9A-Fa-f]{6})") // Updated regex pattern
        val matcher: Matcher = HEX_PATTERN.matcher(message)
        val buffer = StringBuffer()
        while (matcher.find()) {
            try {
                matcher.appendReplacement(
                    buffer,
                    net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString()
                )
            } catch (e: NoSuchMethodError) {
                return message
            }
        }
        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString())
    }
}