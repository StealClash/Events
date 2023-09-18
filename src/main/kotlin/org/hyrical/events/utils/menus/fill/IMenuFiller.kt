package org.hyrical.events.utils.menus.fill

import org.bukkit.entity.Player
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.menus.Menu

interface IMenuFiller {
    fun fill(menu: Menu, player: Player, buttons: MutableMap<Int, Button>, size: Int)
}