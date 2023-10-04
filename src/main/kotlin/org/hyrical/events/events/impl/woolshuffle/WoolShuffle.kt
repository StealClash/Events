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
import com.sk89q.worldedit.regions.Region
import com.sk89q.worldedit.world.block.BlockTypes
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.TimeUtils

object WoolShuffle : Event() {

    val world by lazy {
        Bukkit.getWorld("woolshuffle")!!
    }

    val tasks: MutableMap<Int, BukkitTask?> = mutableMapOf()

    var woolPicked = Material.PINK_WOOL

    val timeUntilNextRound = 5
    val timeUntilPick = 5
    val pickingWoolTime = 3

    var currentTime = 0
    var currentInfo = ""

    val wools: ArrayList<Material> = arrayListOf(
        Material.RED_WOOL,
        Material.BLUE_WOOL,
        Material.CYAN_WOOL,
        Material.LIME_WOOL,
        Material.GREEN_WOOL,
        Material.PURPLE_WOOL,
        Material.PINK_WOOL,
        Material.ORANGE_WOOL,
        Material.YELLOW_WOOL
    )

    override fun getName(): String {
        return "woolshuffle"
    }

    override fun getDisplayName(): String {
        return "Wool Shuffle"
    }

    override fun getScoreboardLines(): MutableList<String> {
        return mutableListOf("", "  $currentInfo")
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf()
    }

    override fun getDeathMessage(player: Player, killer: Player?): String {
        return "&b${player.name} &7has died."
    }

    override fun startEvent() {
        resetWool()
        startIntermission()
    }

    override fun endEvent() {   
        for ((_, task) in tasks){
            task?.cancel()
        }
    }

    fun startIntermission(){
        currentTime = timeUntilPick
        currentInfo = "Round in: &b${TimeUtils.formatDuration(currentTime * 1000L)}"
        tasks[1] = object : BukkitRunnable(){
            override fun run() {
                currentInfo = "Round in: &b${TimeUtils.formatDuration(currentTime * 1000L)}"

                if (currentTime == 0){
                    pickWool()

                    tasks.remove(1)

                    cancel()
                    return
                }

                currentTime--
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun pickWool(){
        currentTime = pickingWoolTime
        woolPicked = wools[EventsServer.RANDOM.nextInt(0, wools.size)]

        for (alive in EventManager.alivePlayers){
            val player = Bukkit.getPlayer(alive) ?: continue

            giveWool(player)
        }

        tasks[2] = object : BukkitRunnable(){
            override fun run() {
                currentInfo = "Find Wool: &e${TimeUtils.formatDuration(currentTime * 1000L)}"

                if (currentTime == 0){
                    tasks.remove(2)

                    removeBlocksInRegionAsync(Location(world, -95.0,72.0,92.0), Location(world, 0.0, 72.0,0.0), woolPicked)
                    newRoundTask()

                    cancel()
                    return
                }

                currentTime--
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun newRoundTask(){
        currentTime = timeUntilNextRound

        tasks[3] = object : BukkitRunnable(){
            override fun run() {
                currentInfo = "Next Round: &c${TimeUtils.formatDuration(currentTime * 1000L)}"

                if (currentTime == 0){
                    resetWool()
                    tasks.remove(3)

                    startIntermission()

                    cancel()
                    return
                }

                currentTime = 0
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun resetWool(){
        val editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld("woolshuffle"))

        val region: Region = CuboidRegion(BukkitWorld(world), BlockVector3.at(-95,72,92), BlockVector3.at(0,72,0))
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

        editSession.setBlocks(region, pattern)
        editSession.flushQueue()
    }

    fun giveWool(player: Player){
        for (x in 0..8){
            player.inventory.addItem(ItemBuilder.of(woolPicked).build())
        }
    }

    private fun removeBlocksInRegionAsync(startLocation: Location, endLocation: Location, avoid: Material) {
        val world = startLocation.world

        val minX = minOf(startLocation.blockX, endLocation.blockX)
        val minY = minOf(startLocation.blockY, endLocation.blockY)
        val minZ = minOf(startLocation.blockZ, endLocation.blockZ)

        val maxX = maxOf(startLocation.blockX, endLocation.blockX)
        val maxY = maxOf(startLocation.blockY, endLocation.blockY)
        val maxZ = maxOf(startLocation.blockZ, endLocation.blockZ)

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    val block = world.getBlockAt(x, y, z)

                    if (block.type == avoid) continue

                    block.type = Material.AIR
                }
            }
        }
    }
}