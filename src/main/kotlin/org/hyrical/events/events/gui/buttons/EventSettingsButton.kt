package org.hyrical.events.events.gui.buttons

import com.sun.jdi.event.EventSet
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.events.gui.EventSettingsMenu
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class EventSettingsButton : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.SLIME_BALL)
            .name(translate("&a&lEvent Settings"))
            .addToLore("", "&fConfigure the settings for event.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        EventSettingsMenu().openMenu(player)
    }
}