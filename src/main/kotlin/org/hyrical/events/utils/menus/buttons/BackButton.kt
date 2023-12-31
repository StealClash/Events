package org.hyrical.events.utils.menus.buttons

import com.cryptomorin.xseries.XMaterial
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu

class BackButton(val menu: Menu) : Button() {

    override fun getItem(player: Player): ItemStack {
        return ItemBuilder(XMaterial.RED_BED.parseMaterial()!!).name((ChatColor.RED).toString() + "Go Back").build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        menu.openMenu(player)
    }
}