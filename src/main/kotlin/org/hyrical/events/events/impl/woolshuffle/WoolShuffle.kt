package org.hyrical.events.events.impl.woolshuffle

import co.aikar.commands.BaseCommand
import com.fastasyncworldedit.core.Fawe
import com.fastasyncworldedit.core.FaweAPI
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.EditSessionBuilder
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.function.pattern.RandomPattern
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.world.block.BlockTypes
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.utils.ItemBuilder

object WoolShuffle : Event() {

    val world by lazy {
        Bukkit.getWorld("woolshuffle")!!
    }

    val woolPicked = Material.PINK_WOOL
    val currentTime = 0
    val currentInfo = ""

    override fun getName(): String {
        return "woolshuffle"
    }

    override fun getDisplayName(): String {
        return "Wool Shuffle"
    }

    override fun getScoreboardLines(): MutableList<String> {
        return mutableListOf("", "")
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf()
    }

    override fun startEvent() {
        resetWool()
        for (alive in EventManager.alivePlayers){
            val player = Bukkit.getPlayer(alive) ?: continue


        }
    }

    fun resetWool(){
        val editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld("woolshuffle"))

        val region = CuboidRegion(BukkitWorld(world), BlockVector3.at(-95,72,92), BlockVector3.at(0,72,0))
        val pattern = RandomPattern()

        pattern.add(BlockTypes.RED_WOOL, 50.0)
        pattern.add(BlockTypes.BLUE_WOOL, 50.0)
        pattern.add(BlockTypes.CYAN_WOOL, 50.0)
        pattern.add(BlockTypes.LIME_WOOL, 50.0)
        pattern.add(BlockTypes.GREEN_WOOL, 50.0)
        pattern.add(BlockTypes.PURPLE_WOOL, 50.0)
        pattern.add(BlockTypes.PINK_WOOL, 50.0)
        pattern.add(BlockTypes.ORANGE_WOOL, 50.0)
        pattern.add(BlockTypes.YELLOW_WOOL, 50.0)

        editSession.setBlocks(region.faces, pattern)
    }

    fun giveWool(player: Player){
        player.inventory.addItem(ItemBuilder.of())
    }
}