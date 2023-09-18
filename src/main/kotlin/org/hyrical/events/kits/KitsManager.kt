package org.hyrical.events.kits

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.hyrical.events.EventsServer
import org.hyrical.events.utils.fromConfig
import org.hyrical.events.utils.saveToConfig

object KitsManager {

    var helmet: ItemStack? = null
    var chestplate: ItemStack? = null
    var leggings: ItemStack? = null
    var boots: ItemStack? = null
    var contents: MutableList<ItemStack?> = mutableListOf()

    fun setItems(inventory: PlayerInventory) {
        contents.addAll(inventory.contents.toList())

        helmet = inventory.helmet
        chestplate = inventory.chestplate
        leggings = inventory.leggings
        boots = inventory.boots
    }

    fun applyKit(player: Player) {
        player.inventory.apply {
            this.helmet = helmet
            this.chestplate = chestplate
            this.leggings = leggings
            this.boots = boots
            this.contents = contents
        }

        player.updateInventory()
    }

    fun saveKit(inventory: PlayerInventory) {
        saveToConfig("kit.helmet", helmet)
        saveToConfig("kit.chestplate", chestplate)
        saveToConfig("kit.leggings", leggings)
        saveToConfig("kit.boots", boots)
        saveToConfig("kit.contents", contents)
    }

    fun deserializeFromConfig() {
        helmet = fromConfig("kit.helmet")
        chestplate = fromConfig("kit.chestplate")
        leggings = fromConfig("kit.leggings")
        boots = fromConfig("kit.boots")
        contents = fromConfig("kit.contents") ?: mutableListOf()
    }
}