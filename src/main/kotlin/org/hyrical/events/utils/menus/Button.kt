package org.hyrical.events.utils.menus

import com.cryptomorin.xseries.XMaterial
import com.cryptomorin.xseries.XSound
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.hyrical.events.utils.ItemBuilder

abstract class Button {
    abstract fun getItem(player: Player): ItemStack
    open fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {

    }

    open fun click(
        player: Player,
        slot: Int,
        clickType: ClickType,
        hotbarButton: Int,
        inventoryClickEvent: InventoryClickEvent
    ) {

    }

    fun getClickSound(player: Player?): ButtonClickSound {
        return ButtonClickSound(XSound.UI_BUTTON_CLICK.parseSound()!!, 1f, 1f)
    }

    var isCancelClick: Boolean = true

    inner class ButtonClickSound @JvmOverloads constructor(
        val sound: Sound,
        var volume: Float = 1f,
        var pitch: Float = 1f
    )

    companion object {
        fun createPlaceholder(material: Material?, subId: Short): Button {
            return createPlaceholder(" ", material, subId)
        }

        fun createPlaceholder(
            displayName: String? = " ",
            material: Material? = XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(),
            subId: Short = 15.toShort()
        ): Button {
            return object : Button() {
                override fun getItem(player: Player): ItemStack {
                    return ItemBuilder(material!!, subId.toInt())
                        .name(displayName)
                        .build()
                }
            }
        }

        fun createPlaceholder(item: ItemStack): Button {
            return object : Button() {
                override fun getItem(player: Player): ItemStack {
                    return item
                }
            }
        }
    }
}