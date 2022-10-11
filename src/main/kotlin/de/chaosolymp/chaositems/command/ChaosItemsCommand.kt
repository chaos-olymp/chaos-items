package de.chaosolymp.chaositems.command

import de.chaosolymp.chaositems.ItemsPlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

internal class ChaosItemsCommand(private val plugin: ItemsPlugin) : CommandExecutor, TabCompleter {
    private val commands = buildList {
        add(GetItemsCommand(plugin))
        add(ReloadCommand(plugin))
    }

    private fun getCommandByLabel(label: String): SubCommand? = commands.firstOrNull { item -> item.name.equals(label, true) }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()) {
            return true
        }

        val subCommandName = args[0]
        val subCommand = getCommandByLabel(subCommandName)

        if(subCommand == null) {
            return false
        } else {
            try {
                subCommand.onCommand(sender, command, label, args.copyOfRange(1, args.size))
            } catch(ex: Exception) {
                sender.sendMessage(plugin.config.messageConfiguration?.commandExceptionOccurred
                    ?: Component.text("Fatal exception occurred").color(TextColor.color(255, 0, 0))
                )
                plugin.logger.severe("Exception of type ${ex.javaClass.simpleName} occurred ${ex.message}")
                ex.printStackTrace()
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        if(args.isNotEmpty()) {
            return if(args.size == 1) {
                commands.map { it.name }.toMutableList()
            } else {
                val subCommandName = args[0]
                val subCommand = getCommandByLabel(subCommandName)
                subCommand?.onTabComplete(sender, command, label, args.copyOfRange(1, args.size)) ?: mutableListOf()
            }
        }
        return mutableListOf()
    }
}