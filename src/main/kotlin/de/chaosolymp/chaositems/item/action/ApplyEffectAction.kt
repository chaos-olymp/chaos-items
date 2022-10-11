package de.chaosolymp.chaositems.item.action

import de.chaosolymp.chaositems.configuration.action.ApplyEffectActionConfiguration
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect

class ApplyEffectAction internal constructor(configuration: ApplyEffectActionConfiguration) : ItemAction() {

    private val potionEffect = PotionEffect(configuration.type!!, configuration.duration!!, configuration.amplifier!!, configuration.ambient, configuration.displayParticles, configuration.showIcon)

    override fun performAction(player: Player) {
        player.addPotionEffect(potionEffect)
    }
}