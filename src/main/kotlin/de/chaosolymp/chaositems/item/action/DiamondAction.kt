package de.chaosolymp.chaositems.item.action

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player

class DiamondAction(private val radius: Int) : ItemAction() {
    override fun performAction(player: Player) {
        for (x in ((player.location.x - radius).toInt())..((player.location.x + radius).toInt())) {
            for(y in ((player.location.y - radius).toInt())..((player.location.y + radius).toInt())) {
                for (z in ((player.location.z - radius).toInt())..((player.location.z + radius).toInt())) {
                    val block = player.world.getBlockAt(x, y, z)
                    if(block.type == Material.COAL_ORE || block.type == Material.COPPER_ORE || block.type == Material.EMERALD_ORE || block.type == Material.GOLD_ORE || block.type == Material.IRON_ORE || block.type == Material.LAPIS_ORE || block.type == Material.REDSTONE_ORE) {
                        block.type = Material.DIAMOND_ORE
                    }
                    if(block.type == Material.DEEPSLATE_COAL_ORE || block.type == Material.DEEPSLATE_COPPER_ORE || block.type == Material.DEEPSLATE_EMERALD_ORE || block.type == Material.DEEPSLATE_GOLD_ORE || block.type == Material.DEEPSLATE_IRON_ORE || block.type == Material.DEEPSLATE_LAPIS_ORE || block.type == Material.DEEPSLATE_REDSTONE_ORE) {
                        block.type = Material.DEEPSLATE_DIAMOND_ORE
                    }
                    if(block.type == Material.NETHER_GOLD_ORE || block.type == Material.NETHER_QUARTZ_ORE) {
                        block.type = if(Math.random() > 0.4) {
                            Material.DIAMOND_ORE
                        } else {
                            Material.DEEPSLATE_DIAMOND_ORE
                        }
                    }
                }
            }
        }

        player.spawnParticle(
            Particle.DRAGON_BREATH,
            player.location,
            3,
            radius.toDouble(),
            radius.toDouble(),
            radius.toDouble()
        )
    }
}