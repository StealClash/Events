package org.hyrical.events.serializers

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.io.IOException

object ItemStackSerializer {

    fun itemToBase64(stack: ItemStack): String {
        val outputStream = ByteArrayOutputStream()
        try {
            val dataOutput = BukkitObjectOutputStream(outputStream)
            dataOutput.writeObject(stack)
            dataOutput.close()
        } catch (e: Exception) {
            throw IllegalStateException("Unable to save item stack.", e)
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray())
    }

    @Throws(IOException::class)
    fun itemFromBase64(data: String): ItemStack {
        val bytes = Base64.getDecoder().decode(data)
        val inputStream = ByteArrayInputStream(bytes)
        try {
            val dataInput = BukkitObjectInputStream(inputStream)
            dataInput.use { dataInput ->
                return dataInput.readObject() as ItemStack
            }
        } catch (e: ClassNotFoundException) {
            throw IOException("Unable to decode class type.", e)
        }
    }
}
