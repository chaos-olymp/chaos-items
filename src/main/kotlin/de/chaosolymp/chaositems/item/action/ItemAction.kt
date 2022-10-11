package de.chaosolymp.chaositems.item.action

import org.bukkit.entity.Player

sealed class ItemAction {
    abstract fun performAction(player: Player)
}