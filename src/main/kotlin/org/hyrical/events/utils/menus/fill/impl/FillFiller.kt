package org.hyrical.events.utils.menus.fill.impl

import com.cryptomorin.xseries.XMaterial
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu
import org.hyrical.events.utils.menus.fill.IMenuFiller
import org.hyrical.events.utils.menus.page.PagedMenu

class FillFiller : IMenuFiller {
    override fun fill(menu: Menu, player: Player, buttons: MutableMap<Int, Button>, size: Int) {
        for (i in (if (menu is PagedMenu) 8 else 0) until size) {
            buttons.putIfAbsent(
                i,
                PlaceholderButton()
            )
        }
    }

    class PlaceholderButton : Button() {
        override fun getItem(player: Player): ItemStack {
            return ItemBuilder.of(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()!!).name(" ").build()
        }
    }
}