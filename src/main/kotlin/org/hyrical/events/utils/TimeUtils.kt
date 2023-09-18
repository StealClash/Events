package org.hyrical.events.utils.time

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt


object TimeUtils {
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm")

    /**
     * Delegate to TimeUtils#formatIntoMMSS for backwards compat
     */
    fun formatIntoHHMMSS(secs: Int): String {
        return formatIntoMMSS(secs)
    }

    /**
     * Formats the time into a format of HH:MM:SS. Example: 3600 (1 hour) displays as '01:00:00'
     *
     * @param secs The input time, in seconds.
     * @return The HH:MM:SS formatted time.
     */
    fun formatIntoMMSS(secs: Int): String {
        // Calculate the seconds to display:
        var secs = secs
        val seconds = secs % 60
        secs -= seconds

        // Calculate the minutes:
        var minutesCount = (secs / 60).toLong()
        val minutes = minutesCount % 60
        minutesCount -= minutes
        val hours = minutesCount / 60
        return (if (hours > 0) (if (hours < 10) "0" else "") + hours + ":" else "") + (if (minutes < 10) "0" else "") + minutes + ":" + (if (seconds < 10) "0" else "") + seconds
    }

    fun formatIntoFancy(millis: Long): String {
        if (millis >= TimeUnit.SECONDS.toMillis(60L)) {
            return formatIntoMMSS((millis / 1000).toInt())
        }
        val seconds = millis / 1000.0
        val d1 = 10.0 * seconds
        return if (seconds > 0.1) {
            (round(d1) / 10.0).toString() + "s"
        } else {
            "0.1s"
        }
    }

    /**
     * Formats time into a detailed format. Example: 600 seconds (10 minutes) displays as '10 minutes'
     *
     * @param secs The input time, in seconds.
     * @return The formatted time.
     */
    fun formatDuration(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt()
        val days = seconds / 86400
        val hours = (seconds % 86400) / 3600
        val minutes = ((seconds % 86400) % 3600) / 60
        val remainingSeconds = seconds % 60

        val formattedTime = StringBuilder()

        if (days > 0) {
            formattedTime.append("${days}d ")
        }

        if (hours > 0) {
            formattedTime.append("${hours}h ")
        }

        if (minutes > 0) {
            formattedTime.append("${minutes}m ")
        }

        if (remainingSeconds > 0) {
            formattedTime.append("${remainingSeconds}s")
        }

        return formattedTime.toString().trim()
    }

    /**
     * Formats time into a format of MM/dd/yyyy HH:mm.
     *
     * @param date The Date instance to format.
     * @return The formatted time.
     */
    fun formatIntoCalendarString(date: Date?): String {
        return dateFormat.format(date)
    }

    /**
     * Parses a string, such as '1h4m25s' into a number of seconds.
     *
     * @param time The string to attempt to parse.
     * @return The number of seconds 'in' the given string.
     */
    fun parseTime(time: String): Int {
        if (time == "0" || time == "") {
            return 0
        }
        val lifeMatch = arrayOf("w", "d", "h", "m", "s")
        val lifeInterval = intArrayOf(604800, 86400, 3600, 60, 1)
        var seconds = 0
        for (i in lifeMatch.indices) {
            val matcher: Matcher = Pattern.compile("([0-9]*)" + lifeMatch[i]).matcher(time)
            while (matcher.find()) {
                seconds += matcher.group(1).toInt() * lifeInterval[i]
            }
        }
        return seconds
    }

    /**
     * Gets the seconds between date A and date B. This will never return a negative number.
     *
     * @param a Date A
     * @param b Date B
     * @return The number of seconds between date A and date B.
     */
    fun getSecondsBetween(a: Date, b: Date): Int {
        return abs((a.time - b.time) as Int / 1000)
    }
}