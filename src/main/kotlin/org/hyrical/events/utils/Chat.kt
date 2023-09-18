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

fun translate(message: String): String{
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

fun convertToSmallCapsFont(input: String): String {
    val smallCapsMap = mapOf(
        'a' to 'ᴀ', 'b' to 'ʙ', 'c' to 'ᴄ', 'd' to 'ᴅ', 'e' to 'ᴇ',
        'f' to 'ғ', 'g' to 'ɢ', 'h' to 'ʜ', 'i' to 'ɪ', 'j' to 'ᴊ',
        'k' to 'ᴋ', 'l' to 'ʟ', 'm' to 'ᴍ', 'n' to 'ɴ', 'o' to 'ᴏ',
        'p' to 'ᴘ', 'q' to 'ǫ', 'r' to 'ʀ', 's' to 's', 't' to 'ᴛ',
        'u' to 'ᴜ', 'v' to 'ᴠ', 'w' to 'ᴡ', 'x' to 'x', 'y' to 'ʏ', 'z' to 'ᴢ'
    )

    val smallCapsStringBuilder = StringBuilder()
    for (char in input) {
        val smallCapsChar = smallCapsMap[char.toLowerCase()] ?: char
        smallCapsStringBuilder.append(smallCapsChar)
    }

    return smallCapsStringBuilder.toString()
}