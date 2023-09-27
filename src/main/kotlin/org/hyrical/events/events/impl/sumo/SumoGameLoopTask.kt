package org.hyrical.events.events.impl.sumo

import org.bukkit.scheduler.BukkitRunnable

class SumoGameLoopTask : BukkitRunnable(){

    var firstTime: Boolean = true

    override fun run() {
        if (firstTime){
            firstTime = false



            return
        }
    }


}