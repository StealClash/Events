package org.hyrical.events.events.impl.fourcorners

import co.aikar.commands.BaseCommand
import org.bukkit.event.Listener
import org.hyrical.events.events.Event
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.hyrical.events.EventsServer

object FourCorners : Event() {
    val world = Bukkit.getWorld("fourcorners")!!

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