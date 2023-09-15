package org.hyrical.events.combat

object CombatManager {

    private var combatEnabled: Boolean = false

    fun isCombatEnabled() = combatEnabled

    fun enableCombat() {
        combatEnabled = true
    }

    fun disableCombat() {
        combatEnabled = false
    }
}