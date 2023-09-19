package org.hyrical.events.events.gui

import org.bukkit.entity.Player
import org.hyrical.events.events.gui.buttons.ChooseEventButton
import org.hyrical.events.events.gui.buttons.EventSettingsButton
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu

class EventsGUI : Menu() {
    init {
        size = 27
    }

    override fun getTitle(player: Player): String {
        return "Event GUI"
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons: MutableMap<Int, Button> = mutableMapOf()

        buttons[14] = EventSettingsButton()
        buttons[12] = ChooseEventButton()

        return buttons
    }
}