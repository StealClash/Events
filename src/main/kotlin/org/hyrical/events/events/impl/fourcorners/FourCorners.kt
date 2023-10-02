package org.hyrical.events.events.impl.fourcorners

import co.aikar.commands.BaseCommand
import me.clip.placeholderapi.util.TimeUtil
import org.bukkit.event.Listener
import org.hyrical.events.events.Event
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.hyrical.events.EventsServer
import org.hyrical.events.utils.TimeUtils
import kotlin.random.Random

object FourCorners : Event() {
    val world = Bukkit.getWorld("fourcorners")!!

    val tasks: MutableMap<Int, BukkitTask?> = mutableMapOf()

    val cornerSelectingTime = 10
    val timeTillDrop = 5
    val timeTillNextRound = 3

    var info = ""
    var currentTime = 0

    var removedCorner: CornerType = CornerType.BLUE

    override fun getName(): String {
        return "fourcorners"
    }

    override fun getDisplayName(): String {
        return "Four Corners"
    }

    override fun getScoreboardLines(): MutableList<String> {
        return mutableListOf()
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf()
    }

    override fun startEvent() {
        gameIntermission()
    }

    override fun endEvent() {
        for ((key, task) in tasks){
            task?.cancel()
        }
    }

    fun gameIntermission(){
        currentTime = cornerSelectingTime
        info = "  Pick a Corner&7: &e${TimeUtils.formatDuration(currentTime * 1000L)}"
        tasks[1] = object : BukkitRunnable() {
            override fun run() {
                info = "  Pick a Corner&7: &e${TimeUtils.formatDuration(currentTime * 1000L)}"

                if (currentTime == 0){
                    removeMiddle()
                    pickCornerIntermission()

                    tasks[1] = null
                    cancel()
                    return
                }

                currentTime--
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun pickCornerIntermission(){
        currentTime = timeTillDrop
        info = "  Drop in&7: &c${TimeUtils.formatDuration(currentTime * 1000L)}"
        tasks[2] = object : BukkitRunnable() {
            override fun run() {
                info = "  Drop in&7: &c${TimeUtils.formatDuration(currentTime * 1000L)}"

                if (currentTime == 0){
                    val availableCorners = CornerType.entries.toTypedArray()
                    val randomCorner = availableCorners[Random.nextInt(0, availableCorners.size)]

                    removedCorner = randomCorner

                    when (randomCorner){
                        CornerType.BLUE -> {
                            removeBlueCorner()
                        }

                        CornerType.RED -> {
                            removeRedCorner()
                        }
                        CornerType.GREEN -> {
                            removeGreenCorner()
                        }
                        CornerType.YELLOW -> {
                            removeYellowCorner()
                        }
                    }

                    nextRoundIntermission()

                    tasks[2] = null
                    cancel()
                    return
                }

                currentTime--
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun nextRoundIntermission(){

        currentTime = timeTillNextRound
        info = "  Next Round&7: &c${TimeUtils.formatDuration(currentTime * 1000L)}"
        tasks[3] = object : BukkitRunnable() {
            override fun run() {
                info = "  Next Round&7: &c${TimeUtils.formatDuration(currentTime * 1000L)}"

                if (currentTime == 0){
                    when (removedCorner){
                        CornerType.BLUE -> {
                            addBlueCorner()
                        }

                        CornerType.RED -> {
                            addRedCorner()
                        }
                        CornerType.GREEN -> {
                            addGreenCorner()
                        }
                        CornerType.YELLOW -> {
                            addYellowCorner()
                        }
                    }

                    addMiddle()
                    gameIntermission()

                    tasks[3] = null
                    cancel()
                    return
                }

                currentTime--
            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun addMiddle(){

    }

    fun removeMiddle(){

    }

    fun addRedCorner(){
        setAirBlocksToSpecificBlockAsync(Location(world, 3.0, 101.0, 2.0), Location(world, 10.0, 101.0, 9.0), Material.RED_WOOL)
    }

    fun addYellowCorner(){
        setAirBlocksToSpecificBlockAsync(Location(world, 10.0, 101.0, -4.0), Location(world, 3.0, 101.0, -11.0), Material.YELLOW_WOOL)

    }

    fun addGreenCorner(){
        setAirBlocksToSpecificBlockAsync(Location(world, -3.0, 101.0, 2.0), Location(world, -10.0, 101.0, 9.0), Material.GREEN_WOOL)

    }

    fun addBlueCorner(){
        setAirBlocksToSpecificBlockAsync(Location(world, -10.0, 101.0, -4.0), Location(world, -3.0, 101.0, -11.0), Material.BLUE_WOOL)
    }
    
    fun removeRedCorner(){
        removeBlocksInRegionAsync(Location(world, 3.0, 101.0, 2.0), Location(world, 10.0, 101.0, 9.0))
    }

    fun removeYellowCorner(){
        removeBlocksInRegionAsync(Location(world, 10.0, 101.0, -4.0), Location(world, 3.0, 101.0, -11.0))

    }

    fun removeGreenCorner(){
        removeBlocksInRegionAsync(Location(world, -3.0, 101.0, 2.0), Location(world, -10.0, 101.0, 9.0))

    }

    fun removeBlueCorner(){
        removeBlocksInRegionAsync(Location(world, -10.0, 101.0, -4.0), Location(world, -3.0, 101.0, -11.0))
    }


    // we don't take a look at this shitr


    fun setAirBlocksToSpecificBlockAsync(startLocation: Location, endLocation: Location, blockType: Material) {
        val scheduler = Bukkit.getScheduler()
        scheduler.runTaskAsynchronously(EventsServer.instance, Runnable {
            for (x in startLocation.blockX..endLocation.blockX) {
                for (y in startLocation.blockY..endLocation.blockY) {
                    for (z in startLocation.blockZ..endLocation.blockZ) {
                        val block = startLocation.world.getBlockAt(x, y, z)
                        if (block.type == Material.AIR) {
                            block.type = blockType
                        }
                    }
                }
            }
        })
    }

    private fun removeBlocksInRegionAsync(startLocation: Location, endLocation: Location) {
        val scheduler = Bukkit.getScheduler()
        scheduler.runTaskAsynchronously(EventsServer.instance, Runnable {
            for (x in startLocation.blockX..endLocation.blockX) {
                for (y in startLocation.blockY..endLocation.blockY) {
                    for (z in startLocation.blockZ..endLocation.blockZ) {
                        val block = startLocation.world.getBlockAt(x, y, z)
                        block.type = Material.AIR
                    }
                }
            }
        })
    }
}