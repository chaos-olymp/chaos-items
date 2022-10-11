package de.chaosolymp.chaositems.item.action

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.data.Ageable
import org.bukkit.entity.Player
import kotlin.math.cos

class FertilizerAction(private val radius: Int) : ItemAction() {
    override fun performAction(player: Player) {
        fertilize(player)
        displayParticleCircle(player)
    }

    private fun fertilize(player: Player) {
        for (x in ((player.location.x - radius).toInt())..((player.location.x + radius).toInt())) {
            for(y in -64..319) {
                for (z in ((player.location.z - radius).toInt())..((player.location.z + radius).toInt())) {
                    val block = player.world.getBlockAt(x, y, z)
                    if(block.blockData is Ageable) {
                        val data = block.blockData as Ageable
                        if(data.age < data.maximumAge) {
                            data.age = data.maximumAge
                        }
                        block.blockData = data
                    }
                }
            }
        }
    }

    private fun displayParticleCircle(player: Player) {
        val particleCount = 100
        for (i in 0..particleCount) {
            val angle = 2 * Math.PI * i / particleCount
            val x = cos(angle) * radius
            val z = cos(angle) * radius
            val baseY = player.location.y

            var y = baseY - 1
            while(y < (baseY + 4)) {
                player.spawnParticle(
                    Particle.SPORE_BLOSSOM_AIR,
                    Location(player.world, x, y, z),
                    2,
                    0.0,
                    0.0,
                    0.0
                )
                y += 0.5
            }
        }
    }
}