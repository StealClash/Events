package org.hyrical.events.kits

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory

object KitsManager {
    var helmet: ItemStack? = null
    var chestplate: ItemStack? = null
    var leggings: ItemStack? = null
    var boots: ItemStack? = null
    var contents: MutableList<ItemStack>? = null

    fun setItems(inventory: PlayerInventory) {
        contents?.addAll(inventory.contents.toCollection())
    }
}