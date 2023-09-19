package org.hyrical.events.events.gui.buttons

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.events.Event
import org.hyrical.events.events.gui.EventTimeGUI
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class TimeMenuButton(val event: Event) : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.CLOCK)
            .name(translate("&a&lEvent Time"))
            .addToLore("", "&fClick to manage the event's time.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        EventTimeGUI(event).openMenu(player)
    }
}