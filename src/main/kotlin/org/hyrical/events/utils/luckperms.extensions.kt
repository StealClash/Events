package org.stealclash.basic.luckperms

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.entity.Player
import org.hyrical.events.utils.extractLastHexCode
import org.hyrical.events.utils.translate

val LP: LuckPerms
    get() = LuckPermsProvider.get()

val Player.coloredName: String
    get()
    {
        val user = LP.userManager.getUser(uniqueId) ?: return ""

        return if (!user.primaryGroup.contains("default")) {
            "&" + user.cachedData.metaData.prefix?.extractLastHexCode() + user.cachedData.metaData.prefix
        } else {
            translate("&f")
        }
    }