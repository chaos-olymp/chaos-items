package de.chaosolymp.chaositems.configuration.action

import org.bukkit.NamespacedKey
import org.bukkit.potion.PotionEffectType


internal class ApplyEffectActionConfiguration: ActionConfiguration(applyEffects) {
    var type: PotionEffectType? = null
    var duration: Int? = null
    var amplifier: Int? = null

    var ambient: Boolean = true
    var displayParticles: Boolean = true
    var showIcon: Boolean = true

    override fun applyToMap(map: MutableMap<String, Any?>) {
        map["type"] = type?.key.toString()
        map["duration"] = duration
        map["amplifier"] = amplifier

        map["ambient"] = ambient
        map["displayParticles"] = displayParticles
        map["showIcon"] = showIcon
    }

    override fun applyFromMap(map: Map<String, Any?>) {
        type = PotionEffectType.getByKey(NamespacedKey.fromString(map["type"] as String))
        duration = map["duration"] as Int?
        amplifier = map["amplifier"] as Int?

        ambient = (map["ambient"] as Boolean?) != false
        displayParticles = (map["displayParticles"] as Boolean?) != false
        showIcon = (map["showIcon"] as Boolean?) != false
    }
}