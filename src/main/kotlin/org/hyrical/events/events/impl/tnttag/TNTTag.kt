package org.hyrical.events.events.impl.tnttag

import co.aikar.commands.BaseCommand
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemFlag
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.commands.EventAdmin
import org.hyrical.events.events.impl.tnttag.listeners.TNTListener
import org.hyrical.events.managers.CombatManager
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.TimeUtils
import org.hyrical.events.utils.translate
import java.util.concurrent.TimeUnit

object TNTTag : Event() {

    val debugMode = false

    var hasStarted = false
    var stopped = false
    var tntPlayers: MutableList<Player> = mutableListOf()

    var roundStarted = 0L
    var timeBetweenMatches = 5
    var round = 0

    val tntInfo: MutableMap<Int, Int> = mutableMapOf(50 to 5, 40 to 4, 30 to 4, 20 to 3, 10 to 2, 2 to 1)
    val baseTime: MutableMap<Int, Int> = mutableMapOf(1 to 50, 2 to 50, 3 to 45, 4 to 40, 5 to 35, 6 to 30, 7 to 30, 8 to 30, 9 to 25, 10 to 20, 11 to 20) // Round -> Time
    val teleport: MutableList<Int> = mutableListOf(1, 5, 6, 7, 8,9, 10, 11)

    var task1: BukkitTask? = null
    var task2: BukkitTask? = null

    var lastSoundAt = 0L

    override fun getName(): String {
        return "tnttag"
    }

    override fun getDisplayName(): String {
        return "TNT Tag"
    }

    override fun getScoreboardLines(): MutableList<String> {
        if (TimeUtils.formatDuration((roundStarted + getBaseTime()) - System.currentTimeMillis()) == "") return mutableListOf("", "  &fExplosion in &c0s", "  &fRound: &e${round}")
        if (((roundStarted + getBaseTime()) - System.currentTimeMillis()) / 1000L == 3L || ((roundStarted + getBaseTime()) - System.currentTimeMillis()) / 1000L == 2L || ((roundStarted + getBaseTime()) - System.currentTimeMillis()) / 1000L == 1L) {
            if (((roundStarted + getBaseTime()) - System.currentTimeMillis()) / 1000L != lastSoundAt) {
                lastSoundAt = ((roundStarted + getBaseTime()) - System.currentTimeMillis()) / 1000L
                Bukkit.getOnlinePlayers().forEach {
                    it.playSound(it, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,1f)
                }
            }
        }
        return mutableListOf("", "  &fExplosion in &c" + TimeUtils.formatDuration((roundStarted + getBaseTime()) - System.currentTimeMillis()), "  &fRound: &e${round}")
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf(TNTListener())
    }

    override fun getDeathMessage(player: Player, killer: Player?): String {
        return "${player.name} &7was blown up."
    }

    override fun startEvent() {
        stopped = false
        round = 0
        CombatManager.enableCombat()

        startRoundSequence()
    }

    fun startRoundSequence(){
        roundStarted = System.currentTimeMillis()
        round += 1

        tntPlayers.clear()
        startRound()
    }

    fun stopRound(){
        tntPlayers = mutableListOf()

        roundStarted = 0L
        timeBetweenMatches = 5
        round = 0

        stopped = true

        task1?.cancel()
        task1 = null

        if (task2 != null){

            task2?.cancel()
        }
        task2 = null
    }

    fun startRound(){
        if (stopped){
            return
        }
        if (EventManager.alivePlayers.size == 1) return

        hasStarted = true

        chooseTNTS()
        chooseRunners()

        Bukkit.getOnlinePlayers().forEach {
            it.sendTitle(translate("&c&lTNT TAG"), translate("&fRound &e$round &fhas started."))
            it.playSound(it, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f,2f)
        }

        Bukkit.broadcastMessage("")
        Bukkit.broadcastMessage(translate("&c&lTNT TAG"))
        Bukkit.broadcastMessage(translate(""))
        val formatted = tntPlayers.map { it.name }.toMutableList().joinToString(", ")
        Bukkit.broadcastMessage(translate("&fThe chosen player(s) that are IT are: $formatted"))
        Bukkit.broadcastMessage("")

        task1 = object : BukkitRunnable(){
            override fun run(){
                if (stopped){
                    cancel()
                    return
                }

                for (player in tntPlayers){
                    player.location.world.strikeLightningEffect(player.location)
                    player.location.world.spawnParticle(Particle.EXPLOSION_HUGE, player.location, 1, 0.0, 0.0, 0.0, 0.0)

                    player.inventory.clear()
                    player.health = 0.0

                    player.sendMessage(translate("&eYou blew up!"))

                    for (plr in getPlayersInRadius(player, 1.0)){
                        player.inventory.clear()
                        plr.health = 0.0
                        plr.sendMessage(translate("&eYou were blown up by another TNTer!"))
                    }
                }

                roundStarted = System.currentTimeMillis()
                round += 1

                tntPlayers.clear()
                startRound()
            }
        }.runTaskLater(EventsServer.instance, (getBaseTime() / 50))
    }

    fun chooseTNTS(){
        val players: ArrayList<Player> = arrayListOf()

        for (player in EventManager.alivePlayers){
            players.add(Bukkit.getPlayer(player)!!)
        }

        players.removeIf { !EventManager.alivePlayers.contains(it.uniqueId)  }

        for (i in 1..getTNTAmount()){
            if (players.isEmpty()) continue

            val player = players[EventsServer.RANDOM.nextInt(0, players.size)]

            players.remove(player)
            tntPlayers.add(player)

            applyTNT(player)
        }
    }

    fun chooseRunners(){
        for (player in Bukkit.getOnlinePlayers()){
            if (teleport.contains(round) || round >= 12){
                if (!EventManager.alivePlayers.contains(player.uniqueId) && player.gameMode == GameMode.SURVIVAL) continue

                EventAdmin.scatter(player, "tnttag")
            }

            if (tntPlayers.contains(player)) continue
            if (!EventManager.alivePlayers.contains(player.uniqueId)) continue

            applyRunner(player)
        }
    }

    override fun revive(player: Player) {
        applyRunner(player)
    }

    fun applyTNT(player: Player){
        player.inventory.clear()
        player.inventory.helmet = ItemBuilder.of(Material.TNT).enchant(Enchantment.KNOCKBACK, 1).flag(ItemFlag.HIDE_ENCHANTS).build()

        for (i in 0..8){
            player.inventory.setItem(i, ItemBuilder.of(Material.TNT).enchant(Enchantment.KNOCKBACK, 1).flag(ItemFlag.HIDE_ENCHANTS).build())
        }

        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE,3,false, false))
    }

    fun applyRunner(player: Player){
        player.inventory.clear()
        player.inventory.helmet = null

        player.removePotionEffect(PotionEffectType.SPEED)
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE,2,false, false))
    }

    fun getPlayersInRadius(player: Player, radius: Double): List<Player> {
        val nearbyPlayers = mutableListOf<Player>()
        for (entity in player.getNearbyEntities(radius, radius, radius)) {
            if (entity is Player) {
                nearbyPlayers.add(entity)
            }
        }
        return nearbyPlayers
    }

    fun getTNTAmount(): Int {
        val players = EventManager.alivePlayers.size

        for ((key, value) in tntInfo){
            if (players >= key){
                return value
            }
        }

        return 1
    }

    fun getBaseTime(): Long {
        val valueForKey = baseTime[round]

        if (valueForKey != null) {
            return TimeUnit.SECONDS.toMillis(valueForKey.toLong())
        }

        return TimeUnit.SECONDS.toMillis(20L)
    }
}