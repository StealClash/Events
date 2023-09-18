package org.hyrical.events.utils.menus.buttons

import com.cryptomorin.xseries.XMaterial
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button

class CloseButton : Button() {

    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(XMaterial.RED_BED.parseMaterial()!!)
            .name("&c&lClose")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        player.closeInventory()
    }
}