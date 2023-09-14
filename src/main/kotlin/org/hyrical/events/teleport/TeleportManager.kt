package org.hyrical.events.teleport

import org.bukkit.Location

object TeleportManager {

    var teleportLocation: Location? = null

    fun hasTeleportLocation() = teleportLocation != null
}