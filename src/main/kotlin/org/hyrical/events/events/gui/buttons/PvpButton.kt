package org.hyrical.events.events.gui.buttons

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.managers.CombatManager
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.convertToSmallCapsFont
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class PvpButton : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.DIAMOND_SWORD)
            .name(translate("&c&lPvP Mode"))
            .addToLore("", "&fClick to enable/disable player attacking for the event.", "", "&fStatus: " + (
                    if (CombatManager.isCombatEnabled()) "&a" + convertToSmallCapsFont("on") else "&c" + convertToSmallCapsFont("off")
                    ))
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        player.closeInventory()

        if (CombatManager.isCombatEnabled()){
            CombatManager.disableCombat()
            player.sendTitle(translate("&c&lToggled off PvP"), translate("&7Players can't &cPvP!"))
        } else {
            CombatManager.enableCombat()
            player.sendTitle(translate("&a&lToggled on PvP"), translate("&7Players can now &aPvP!"))
        }

    }
}