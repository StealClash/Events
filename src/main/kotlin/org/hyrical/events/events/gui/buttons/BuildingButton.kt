package org.hyrical.events.events.gui.buttons

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.managers.BuildManager
import org.hyrical.events.managers.CombatManager
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.convertToSmallCapsFont
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class BuildingButton : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.DIAMOND_PICKAXE)
            .name(translate("&e&lBuild Mode"))
            .addToLore("", "&fClick to enable/disable building for the event.", "", "&fStatus: " + (
                    if (BuildManager.isBuildingEnabled()) "&a" + convertToSmallCapsFont("on") else "&c" + convertToSmallCapsFont("off")
                    ))
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        player.closeInventory()

        if (BuildManager.isBuildingEnabled()){
            BuildManager.disableBuilding()
            player.sendTitle(translate("&c&lToggled off Building"), translate("&7Players can't &cBuild!"))
        } else {
            BuildManager.enableBuilding()
            player.sendTitle(translate("&a&lToggled on Building"), translate("&7Players can now &aBuild!"))
        }
        
    }
}