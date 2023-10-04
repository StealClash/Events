package org.hyrical.events.events.impl.fourcorners

import co.aikar.commands.BaseCommand
import me.clip.placeholderapi.util.TimeUtil
import org.bukkit.event.Listener
import org.hyrical.events.events.Event
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.hyrical.events.EventsServer
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.events.impl.fourcorners.listeners.FourCornersListener
import org.hyrical.events.utils.TimeUtils
import org.hyrical.events.utils.translate
import kotlin.random.Random

object FourCorners : Event() {
    val world by lazy {
        Bukkit.getWorld("fourcorners")!!
    }

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
        return mutableListOf("", info)
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf(FourCornersListener())
    }

    override fun getDeathMessage(player: Player, killer: Player?): String {
        return "&b${player.name} &7has died."
    }

    override fun startEvent() {
        for (alive in EventManager.alivePlayers){
            EventAdmin.scatter(Bukkit.getPlayer(alive)!!, "fourcorners")
        }
        gameIntermission()

        Bukkit.broadcastMessage(translate("&7[&b&lEvents&7] &fFour corners has been started. To play, select a random color and then after a certain amount of time, the middle will drop and it will pick randomly between the 4 colors to set to air. If your corner gets set to air, you're out."))

    }

    override fun endEvent() {
        info = ""
        currentTime = 0

        for ((key, task) in tasks){
            task?.cancel()
        }

        addMiddle()
        addRedCorner()
        addGreenCorner()
        addYellowCorner()
        addBlueCorner()
    }

    fun gameIntermission(){
        currentTime = cornerSelectingTime
        info = "  Pick a Corner&7: &e${TimeUtils.formatDuration(currentTime * 1000L)}"
        tasks[1] = object : BukkitRunnable() {
            override fun run() {

                if (currentTime == 0){
                    removeMiddle()
                    pickCornerIntermission()

                    Bukkit.broadcastMessage(translate("&7[&b&lEvents&7] &fThe middle has been removed!"))


                    Bukkit.getOnlinePlayers().forEach { it.playSound(it.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,2f) }

                    tasks[1] = null
                    cancel()
                    return
                }


                info = "  Pick a Corner&7: &e${TimeUtils.formatDuration(currentTime * 1000L)}"



                if (currentTime == 2 || currentTime == 1){
                    Bukkit.getOnlinePlayers().forEach { it.playSound(it.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f) }
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


                if (currentTime == 0){
                    val availableCorners = CornerType.entries.toTypedArray()
                    val randomCorner = availableCorners[EventsServer.RANDOM.nextInt(0, availableCorners.size)]

                    removedCorner = randomCorner

                    Bukkit.broadcastMessage(translate("&7[&b&lEvents&7] &fThe color that was chosen was: ${randomCorner.displayName}"))


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
                    Bukkit.getOnlinePlayers().forEach { it.playSound(it.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,2f) }


                    nextRoundIntermission()

                    tasks[2] = null
                    cancel()
                    return
                }

                info = "  Drop in&7: &c${TimeUtils.formatDuration(currentTime * 1000L)}"



                if (currentTime == 2 || currentTime == 1){
                    Bukkit.getOnlinePlayers().forEach { it.playSound(it.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f) }
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

                if (currentTime == 0){
                    Bukkit.broadcastMessage(translate("&7[&b&lEvents&7] &fStarting new round. Choose your corners."))

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

                    Bukkit.getOnlinePlayers().forEach { it.playSound(it.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,2f) }

                    addMiddle()
                    gameIntermission()

                    tasks[3] = null
                    cancel()
                    return
                }


                info = "  Next Round&7: &c${TimeUtils.formatDuration(currentTime * 1000L)}"


                if (currentTime == 2 || currentTime == 1){
                    Bukkit.getOnlinePlayers().forEach { it.playSound(it.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f) }
                }

                currentTime--

            }
        }.runTaskTimer(EventsServer.instance, 0L, 20L)
    }

    fun addMiddle(){
        setAirBlocksToSpecificBlockAsync(Location(world, 1.0, 101.0, -13.0), Location(world, -1.0,101.0,13.0), Material.STONE_BRICKS)
        setAirBlocksToSpecificBlockAsync(Location(world, 13.0, 101.0, 0.0), Location(world, -13.0,101.0,-2.0), Material.STONE_BRICKS)
    }

    fun removeMiddle(){
        removeBlocksInRegionAsync(Location(world, 1.0, 101.0, -13.0), Location(world, -1.0,101.0,13.0))
        removeBlocksInRegionAsync(Location(world, 13.0, 101.0, 0.0), Location(world, -13.0,101.0,-2.0))
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
        removeBlocksInRegionAsync(Location(world, 10.0, 101.0, -11.0), Location(world, 3.0, 101.0, -4.0))

    }

    fun removeGreenCorner(){
        removeBlocksInRegionAsync(Location(world, -3.0, 101.0, 2.0), Location(world, -10.0, 101.0, 9.0))

    }

    fun removeBlueCorner(){
        removeBlocksInRegionAsync(Location(world, -10.0, 101.0, -4.0), Location(world, -3.0, 101.0, -11.0))
    }


    // we don't take a look at this shitr


    fun setAirBlocksToSpecificBlockAsync(startLocation: Location, endLocation: Location, blockType: Material) {
        val world = startLocation.world

        val minX = minOf(startLocation.x.toInt(), endLocation.x.toInt())
        val minY = minOf(startLocation.y.toInt(), endLocation.y.toInt())
        val minZ = minOf(startLocation.z.toInt(), endLocation.z.toInt())

        val maxX = maxOf(startLocation.x.toInt(), endLocation.x.toInt())
        val maxY = maxOf(startLocation.y.toInt(), endLocation.y.toInt())
        val maxZ = maxOf(startLocation.z.toInt(), endLocation.z.toInt())

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    val block = world.getBlockAt(x, y, z)
                    if (block.type == Material.AIR) {
                        block.type = blockType
                    }
                }
            }
        }
    }

    private fun removeBlocksInRegionAsync(startLocation: Location, endLocation: Location) {
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
                    block.type = Material.AIR
                }
            }
        }
    }
}