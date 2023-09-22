package org.hyrical.events.events.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu
import org.hyrical.events.events.Event
import org.hyrical.events.events.gui.buttons.PvpButton
import org.hyrical.events.events.gui.buttons.ScatterButton

class EventTimeGUI(val event: Event) : Menu() {

    init {
        size = 27
    }

    override fun getTitle(player: Player): String {
        return "Manage time ${event.getName()}"
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons: MutableMap<Int, Button> = mutableMapOf()

        buttons[12] =

        return buttons
    }

    override fun onClose(player: Player, inventory: Inventory) {
        object : BukkitRunnable() {
            override fun run() {
                ManageEventGUI(event).openMenu(player)
            }
        }.runTaskLater(EventsServer.instance, 1L)
    }
}