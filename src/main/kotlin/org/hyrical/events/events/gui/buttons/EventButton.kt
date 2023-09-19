package org.hyrical.events.events.gui.buttons

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.events.gui.EventSettingsMenu
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate
import org.hyrical.events.events.Event
import org.hyrical.events.events.gui.ManageEventGUI

class EventButton(val event: Event) : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.AMETHYST_SHARD)
            .name(translate("&d&l${event.getName()}"))
            .addToLore("", "&fClick to configure this event.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        // open manage gui
        ManageEventGUI()
    }
}