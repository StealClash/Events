package org.hyrical.events.events.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.gui.buttons.*
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu

class ChooseEventGUI :  Menu() {
    override fun getTitle(player: Player): String {
        return "Choose Event"
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons: MutableMap<Int, Button> = mutableMapOf()
        var i = 0

        for (event in EventManager.events){
            buttons[i++] = EventButton(event)
        }

        return buttons
    }

    override fun onClose(player: Player, inventory: Inventory) {
        object : BukkitRunnable() {
            override fun run() {
                EventsGUI().openMenu(player)
            }
        }.runTaskLater(EventsServer.instance, 1L)
    }
}