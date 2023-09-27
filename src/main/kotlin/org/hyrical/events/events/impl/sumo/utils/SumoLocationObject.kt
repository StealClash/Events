package org.hyrical.events.events.impl.sumo.utils

import co.aikar.commands.BaseCommand
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

data class SumoLocationObject(
    var location1: Location?,
    var location2: Location?,
)