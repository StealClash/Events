package org.hyrical.events.events

import co.aikar.commands.BaseCommand
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

data class EventObject(
    var name: String,
    var location1: Location?,
    var location2: Location?,
)