package de.chaosolymp.chaositems.item.action

import org.bukkit.Particle
import org.bukkit.entity.Player
import kotlin.math.abs

object HealAction : ItemAction() {
    override fun performAction(player: Player) {
        val healthOffset = 20.0 - player.health

        player.health = 20.0
        player.spawnParticle(Particle.HEART, player.location, abs(healthOffset).toInt())
    }
}