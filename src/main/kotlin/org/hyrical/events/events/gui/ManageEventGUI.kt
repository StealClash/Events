package org.hyrical.events.events.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu
import org.hyrical.events.events.Event
import org.hyrical.events.events.gui.buttons.*

class ManageEventGUI(val event: Event) : Menu() {

    init {
        size = 27
    }

    override fun getTitle(player: Player): String {
        return "Manage ${event.getName()}"
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons: MutableMap<Int, Button> = mutableMapOf()

        buttons[11] = ScatterButton(event)
        buttons[13] = EventStartButton(event)
        buttons[15] = TimeMenuButton(event)
        buttons[17] = SetNameButton(event)

        return buttons
    }
}