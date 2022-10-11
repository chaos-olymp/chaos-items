package de.chaosolymp.chaositems.command

import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter

internal sealed class SubCommand(val name: String): CommandExecutor, TabCompleter
