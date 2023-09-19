package org.hyrical.events.events.gui.buttons

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.events.gui.ChooseEventGUI
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class ChooseEventButton : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.EMERALD)
            .name(translate("&a&lEvents"))
            .addToLore("", "&fLook for all listed events here and start them.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        ChooseEventGUI().openMenu(player)
    }
}