package de.chaosolymp.chaositems.command

import de.chaosolymp.chaositems.ItemsPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

internal class GetItemsCommand(private val plugin: ItemsPlugin): SubCommand("get-items") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(!sender.hasPermission("chaositems.command.chaositems.getitems")) {
            sender.sendMessage(plugin.config.messageConfiguration?.noPermission!!)
            return true
        }

        val player = sender as? Player
        if(player == null) {
            sender.sendMessage(plugin.config.messageConfiguration?.notAPlayer!!)
            return true
        }

        plugin.getItems().forEach { item -> player.inventory.addItem(item) }
        sender.sendMessage(plugin.config.messageConfiguration?.getItemsSuccess!!)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        return mutableListOf()
    }
}