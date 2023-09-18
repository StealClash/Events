package org.hyrical.events.managers

object BuildManager {

    private var buildingEnabled: Boolean = false

    fun isBuildingEnabled() = buildingEnabled

    fun enableBuilding() {
        buildingEnabled = true
    }

    fun disableBuilding() {
        buildingEnabled = false
    }
}