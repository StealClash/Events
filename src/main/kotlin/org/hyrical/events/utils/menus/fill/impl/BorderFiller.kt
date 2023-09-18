package org.hyrical.events.utils.menus.fill.impl

import com.cryptomorin.xseries.XMaterial
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu
import org.hyrical.events.utils.menus.fill.IMenuFiller
import org.hyrical.events.utils.menus.page.PagedMenu

class BorderFiller : IMenuFiller {
    override fun fill(menu: Menu, player: Player, buttons: MutableMap<Int, Button>, size: Int) {
        val startIndex = if (menu is PagedMenu) 8 else 0
        for (i in startIndex until size) {
            if (i < startIndex + 9) {
                buttons.putIfAbsent(i, PlaceholderButton())
                buttons.putIfAbsent(i + (size - 9), PlaceholderButton())
            }
            if (i % 9 == 0) {
                buttons.putIfAbsent(i, PlaceholderButton())
                buttons.putIfAbsent(i + 8, PlaceholderButton())
            }
        }
    }

    class PlaceholderButton : Button() {
        override fun getItem(player: Player): ItemStack {
            return ItemBuilder.of(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()!!).name(" ").build()
        }
    }
}