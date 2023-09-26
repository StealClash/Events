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

class EventStartButton(val event: Event) : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.EMERALD)
            .name(translate("&a&lStart Event"))
            .addToLore("", "&fClick to start the event.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        EventManager.updateAlivePlayers()
        EventManager.currentEvent = event
        event.startEvent()

        if (event.giveKitOnStart() == "") return

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit all ${event.giveKitOnStart()} ${player.name}")
    }
}