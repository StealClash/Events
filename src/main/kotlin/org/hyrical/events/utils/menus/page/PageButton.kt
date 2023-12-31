package org.hyrical.events.utils.menus.page

import com.cryptomorin.xseries.XMaterial
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class PageButton(private val mod: Int, private val menu: PagedMenu) : Button() {
    override fun getItem(player: Player): ItemStack {
        return if (hasNext(player)) {
            ItemBuilder(XMaterial.GRAY_CARPET.parseMaterial()!!)
                .name(translate(if (mod == 0) "&7Previous Page" else "&7Next page"))
                .amount(1)
                .data(7)
                .build()
        } else {
            ItemBuilder(XMaterial.GRAY_CARPET.parseMaterial()!!)
                .name(translate(if (mod == 0) "&7Previous Page" else "&7Next page"))
                .amount(1)
                .data(7)
                .build()
        }
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        if (clickType.isShiftClick) {
            if (hasNext(player)) {
                menu.modPage(player, if (mod > 0) menu.getPages(player) - menu.page else 1 - menu.page)
            }
        } else {
            if (hasNext(player)) {
                menu.modPage(player, mod)
            }
        }
    }

    private fun hasNext(player: Player): Boolean {
        val pg = menu.page + mod
        return pg > 0 && menu.getPages(player) >= pg
    }
}