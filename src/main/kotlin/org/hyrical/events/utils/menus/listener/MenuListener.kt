package org.hyrical.events.utils.menus.listener

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu
import org.hyrical.events.utils.menus.hotbaritem.HotbarItem
import org.hyrical.events.utils.menus.page.PagedMenu
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


object MenuListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        val player: Player = event.whoClicked as Player
        val menu: Menu = Menu.openedMenus[player] ?: return
        if (event.clickedInventory == null) {
            return
        }
        if (event.clickedInventory !== player.openInventory.topInventory) {
            if (menu.cancelLowerClicks()) event.isCancelled = true
            return
        }
        if (menu.cancelClicks()) {
            event.isCancelled = true
        }
        val slot: Int = event.slot
        val buttons: Map<Int, Button> = menu.getButtons(player)
        if (buttons.containsKey(slot)) {
            val button: Button = buttons[slot]!!
            button.click(player, slot, event.click, event.hotbarButton)
            event.isCancelled = button.isCancelClick
            val sound: Button.ButtonClickSound = button.getClickSound(player)
            player.playSound(player.location, sound.sound, sound.volume, sound.pitch)
            if (menu.isClickUpdate) {
                menu.updateInventory(player, menu is PagedMenu)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player: Player = event.player as Player
        val menu: Menu = Menu.openedMenus[player] ?: return
        menu.cancelIncomingUpdates = true
        menu.onClose(player, event.inventory)
        if (menu.updateRunnable != null) {
            menu.updateRunnable!!.cancel()
            menu.updateRunnable = null
        }
        Menu.openedMenus.remove(player)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player: Player = event.player
        val menu: Menu = Menu.openedMenus[player] ?: return
        menu.onClose(player, event.player.openInventory.topInventory)
        if (menu.updateRunnable != null) {
            menu.updateRunnable!!.cancel()
            menu.updateRunnable = null
        }
        Menu.openedMenus.remove(player)
        HotbarItem.HOTBAR_ITEMS.remove(player.uniqueId)
    }
}