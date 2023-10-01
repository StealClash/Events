package org.hyrical.events.events.impl.tnttag

import co.aikar.commands.BaseCommand
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.Listener
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

    var hasStarted = false
    var tntPlayers: MutableList<Player> = mutableListOf()

    var roundStarted = 0L
    var timeBetweenMatches = 5
    var round = 0

    val tntInfo: MutableMap<Int, Int> = mutableMapOf(1 to 1, 2 to 4, 3 to 4, 4 to 3, 5 to 3, 6 to 3, 7 to 2, 8 to 1, 9 to 1, 10 to 1, 11 to 1) // Round -> Chosen Players
    val baseTime: MutableMap<Int, Int> = mutableMapOf(1 to 50, 2 to 50, 3 to 45, 4 to 40, 5 to 30, 6 to 25, 7 to 20, 8 to 15, 9 to 15, 10 to 15, 11 to 15) // Round -> Time
    val teleport: MutableList<Int> = mutableListOf(1, 5, 6, 7, 8,9, 10, 11)

    var task1: BukkitTask? = null

    override fun getName(): String {
        return "tnttag"
    }

    override fun getDisplayName(): String {
        return "TNT Tag"
    }

    override fun getScoreboardLines(): MutableList<String> {
        if (TimeUtils.formatDuration((roundStarted + getBaseTime()) - System.currentTimeMillis()) == "") return mutableListOf("", "  &fExplosion in &c0s")
        return mutableListOf("", "  &fExplosion in &c" + TimeUtils.formatDuration((roundStarted + getBaseTime()) - System.currentTimeMillis()) )
    }

    override fun getCommands(): ArrayList<BaseCommand> {
        return arrayListOf()
    }

    override fun getListeners(): ArrayList<Listener> {
        return arrayListOf(TNTListener())
    }

    override fun startEvent() {
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

        task1?.cancel()
        task1 = null
    }

    fun startRound(){
        hasStarted = true

        chooseTNTS()

        for (player in Bukkit.getOnlinePlayers()){
            if (teleport.contains(round)){
                EventAdmin.scatter(player, "tnttag")
            }

            if (tntPlayers.contains(player)) continue
            if (!EventManager.alivePlayers.contains(player.uniqueId)) continue

            applyRunner(player)
        }

        Bukkit.broadcastMessage("")
        Bukkit.broadcastMessage(translate("&c&lTNT TAG"))
        Bukkit.broadcastMessage(translate(""))
        val formatted = tntPlayers.map { it.name }.toMutableList().joinToString(", ")
        Bukkit.broadcastMessage(translate("&fThe chosen player(s) that are IT are: $formatted"))
        Bukkit.broadcastMessage("")

        task1 = object : BukkitRunnable(){
            override fun run(){
                for (player in tntPlayers){
                    player.location.world.strikeLightningEffect(player.location)
                    player.location.world.spawnParticle(Particle.EXPLOSION_HUGE, player.location, 1, 0.0, 0.0, 0.0, 0.0)

                    player.inventory.clear()
                    player.health = 0.0

                    player.sendMessage(translate("&eYou blew up!"))

                    for (plr in getPlayersInRadius(player, 2.0)){
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
        val players: MutableList<Player> = mutableListOf()
        players.addAll(Bukkit.getOnlinePlayers())

        println(getTNTAmount())

        for (i in 1..getTNTAmount()){
            if (players.isEmpty()) continue

            val player = players[EventsServer.RANDOM.nextInt(0, players.size)]

            players.remove(player)
            tntPlayers.add(player)

            applyTNT(player)
        }
    }

    fun applyTNT(player: Player){
        player.inventory.clear()
        player.inventory.helmet = ItemBuilder.of(Material.TNT).enchant(Enchantment.DAMAGE_ALL, 1).build()

        for (i in 0..8){
            player.inventory.setItem(i, ItemBuilder.of(Material.TNT).enchant(Enchantment.DAMAGE_ALL, 1).build())
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
        val valueForKey = tntInfo[round]

        if (valueForKey != null) {
            return valueForKey
        }
        Bukkit.broadcastMessage("LOL")
        return 0
    }

    fun getBaseTime(): Long {
        val valueForKey = baseTime[round]

        if (valueForKey != null) {
            return TimeUnit.SECONDS.toMillis(valueForKey.toLong())
        }

        Bukkit.broadcastMessage("lol")

        return 0L
    }
}