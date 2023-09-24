package org.hyrical.events.kits

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.hyrical.events.EventsServer
import org.hyrical.events.serializers.ItemStackSerializer

object KitsManager {

    /*
    val config = EventsServer.instance.config

    fun createKit(kit: String){
        config.set("kits.$kit.contents", arrayListOf<String>())
        EventsServer.instance.saveConfig()
    }

    fun saveItems(kit: String, contents: MutableList<ItemStack>){
        val deserializedContent: ArrayList<String> = arrayListOf()

        for (content in contents.filterNotNull()){
            deserializedContent.add(ItemStackSerializer.itemToBase64(content))
        }

        for (deserialized in deserializedContent){
            Bukkit.broadcastMessage(deserialized)

        }

        config.set("kits.$kit.contents", contents)

        EventsServer.instance.saveConfig()
    }

    fun getItems(kit: String): ArrayList<ItemStack>{
        val deserialized = arrayListOf<ItemStack>()

        for (content in config.("kits.$kit.contents")!!){
            deserialized.add(ItemStackSerializer.itemFromBase64(content))
        }

        Bukkit.broadcastMessage(config.getList("kits.$kit.contents"))

        return deserialized
    }

    fun getAllKits(): ArrayList<String> {
        val configSection: ConfigurationSection = config.getConfigurationSection("kits")!!
        val kits: ArrayList<String> = arrayListOf()

        for (key in configSection.getKeys(false)){
            kits.add(key)
        }

        return kits
    }

    fun deleteKit(kit: String){
        config.set("kits.$kit", null)
        EventsServer.instance.saveConfig()

    }

     */
}