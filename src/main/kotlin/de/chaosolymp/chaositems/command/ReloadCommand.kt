package de.chaosolymp.chaositems.command

import de.chaosolymp.chaositems.ItemsPlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import java.io.IOException

internal class ReloadCommand(private val plugin: ItemsPlugin): SubCommand("reload") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.hasPermission("chaositems.command.chaositems.reload")) {
            sender.sendMessage(plugin.config.messageConfiguration?.noPermission!!)
            return true
        }
        if(args.isNotEmpty()) {
            when(args[0].lowercase()) {
                "config" -> {
                    try {
                        plugin.loadConfiguration()
                        sender.sendMessage(plugin.config.messageConfiguration?.reloadConfigSuccess!!)
                    } catch(ex: SecurityException) {
                        sender.sendMessage(plugin.config.messageConfiguration?.reloadConfigSecurityException!!)
                    } catch(ex: IOException) {
                        sender.sendMessage(plugin.config.messageConfiguration?.reloadConfigIoException!!)
                    }
                }
                "full" -> {
                    val pluginManager = plugin.server.pluginManager
                    pluginManager.disablePlugin(plugin)
                    pluginManager.enablePlugin(plugin)

                    if(pluginManager.isPluginEnabled(plugin)) {
                        sender.sendMessage(plugin.config.messageConfiguration?.reloadFullSuccess!!)
                    } else {
                        sender.sendMessage(plugin.config.messageConfiguration?.reloadFullFailed!!)
                    }
                }
                else -> {
                    sender.sendMessage(plugin.config.messageConfiguration?.reloadInvalidArgument!!)
                }
            }
        } else {
            sender.sendMessage(plugin.config.messageConfiguration?.reloadInvalidArgumentCount!!)
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String> {
        if(args.size == 1) {
            return mutableListOf("config", "full")
        }
        return mutableListOf()
    }
}