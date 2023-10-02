package org.hyrical.events.events.gui.buttons

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.events.gui.EventSettingsMenu
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager

class SetNameButton(val event: Event) : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.EMERALD)
            .name(translate("&a&lSet Event Name"))
            .addToLore("", "&fClick to set event name on scoreboard.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        EventManager.currentEvent = event
        player.sendMessage(translate("&aUpdated event name."))
    }
}