package org.hyrical.events.events.impl.spleef

import co.aikar.commands.BaseCommand
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.function.pattern.RandomPattern
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.regions.Region
import com.sk89q.worldedit.world.block.BlockState
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.events.impl.spleef.commands.SetupSpleef
import org.hyrical.events.events.impl.spleef.listener.SpleefListener
import org.hyrical.events.managers.BuildManager
import org.hyrical.events.managers.CombatManager
import org.hyrical.events.utils.ItemBuilder


object Spleef : Event() {
    override fun getName(): String {
        return "spleef"
    }

    override fun getDisplayName(): String {
        return "Spleef"
    }

    override fun getScoreboardLines(): MutableList<String> {
        return mutableListOf()
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf(SpleefListener())
    }

    override fun startEvent() {
        for (player in EventManager.alivePlayers){
            EventAdmin.scatter(Bukkit.getPlayer(player)!!, "spleef")
        }

        CombatManager.disableCombat()
        BuildManager.enableBuilding()

        Bukkit.getWorld("spleef_map")!!.setGameRule(GameRule.FALL_DAMAGE, false)

        for (player in EventManager.alivePlayers){
            applyKit(Bukkit.getPlayer(player)!!)
        }
    }

    fun applyKit(player: Player){
        player.inventory.setItem(0, ItemBuilder.of(Material.DIAMOND_SHOVEL).enchant(Enchantment.DIG_SPEED, 5).unbreakable(true).build())
        player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, Int.MAX_VALUE,0,false,false))
        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, Int.MAX_VALUE,1,false,false))
    }

    /*
    private fun setBlock(x: Double, y: Double, z: Double, x1: Double, y1: Double,z1: Double, world: String){
        WorldEdit.getInstance().newEditSessionBuilder().world(BukkitAdapter.adapt(Bukkit.getWorld("spleef_map"))).maxBlocks(32768).build().use { editSession ->
            val minVector = BlockVector3.at(x, y,z)
            val maxVector = BlockVector3.at(x1,y1,z1)

            val region = CuboidRegion(minVector, maxVector)

            val pattern = RandomPattern()
            val block: BlockState = BukkitAdapter.adapt(Material.SNOW_BLOCK.createBlockData())
            pattern.add(block, 100.0)

            editSession.setBlocks(region as Region, pattern)

            editSession.flushQueue()
            editSession.close()
        }
    }


    fun cleanUpBlocks(){
        setBlock(39.0, 97.0, 36.0, -22.0,97.0,-28.0,"spleef_map")
        setBlock(39.0, 89.0, 36.0, -22.0,89.0,-28.0,"spleef_map")
        setBlock(39.0, 81.0, 36.0, -22.0,81.0,-28.0,"spleef_map")
        setBlock(39.0, 65.0, 36.0, -22.0,65.0,-28.0,"spleef_map")
        setBlock(39.0, 73.0, 36.0, -22.0,73.0,-28.0,"spleef_map")
    }

     */
}