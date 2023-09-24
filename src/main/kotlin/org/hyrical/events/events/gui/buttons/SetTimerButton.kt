package org.hyrical.events.events.gui.buttons

import org.bukkit.Material
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.ConversationFactory
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.conversations.ValidatingPrompt
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack
import org.hyrical.events.EventsServer
import org.hyrical.events.events.Event
import org.hyrical.events.events.EventManager
import org.hyrical.events.events.gui.EventTimeGUI
import org.hyrical.events.utils.ItemBuilder
import org.hyrical.events.utils.TimeUtils
import org.hyrical.events.utils.menus.Button
import org.hyrical.events.utils.translate

class SetTimerButton(val event: Event) : Button() {
    override fun getItem(player: Player): ItemStack {
        return ItemBuilder.of(Material.NAME_TAG)
            .name(translate("&a&lSet Time"))
            .addToLore("", "&fClick to set the event's time to start.")
            .build()
    }

    override fun click(player: Player, slot: Int, clickType: ClickType, hotbarButton: Int) {
        player.closeInventory()
        val conversationFactory = ConversationFactory(EventsServer.instance)
            .withModality(true)
            .withFirstPrompt(object : StringPrompt() {
                override fun getPromptText(context: ConversationContext): String {
                    return translate("&eType in chat for what time you would like. Type &ccancel &eto cancel.")
                }

                override fun acceptInput(context: ConversationContext, input: String?): Prompt? {
                    val p = context.forWhom as Player
                    val time = TimeUtils.parseTime(input!!.lowercase())

                    if (time == 0) {
                        p.sendRawMessage(translate("&cFailed to input time correctly."))
                        EventTimeGUI(event).openMenu(p)

                        return this
                    }

                    EventManager.timeLeft = ((time * 1000) + System.currentTimeMillis())
                    EventManager.baseTime = ((time * 1000)).toLong()
                    p.sendRawMessage(translate("&aSet timer"))

                    return END_OF_CONVERSATION
                }
            })
            .withEscapeSequence("cancel")
            .withTimeout(60) // Timeout in seconds

        val conversation = conversationFactory.buildConversation(player)
        conversation.begin()
    }
}