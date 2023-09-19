package org.hyrical.events.events.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitRunnable
import org.hyrical.events.EventsServer
import org.hyrical.events.events.gui.buttons.BuildingButton
import org.hyrical.events.events.gui.buttons.ChooseEventButton
import org.hyrical.events.events.gui.buttons.EventSettingsButton
import org.hyrical.events.events.gui.buttons.PvpButton
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu

class EventSettingsMenu :  Menu() {
    override fun getTitle(player: Player): String {
        return "Manage Event Settings"
    }

    init {
        size = 27
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons: MutableMap<Int, Button> = mutableMapOf()

        buttons[12] = PvpButton()
        buttons[14] = BuildingButton()

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