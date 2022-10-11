package de.chaosolymp.chaositems.configuration.trigger

import de.chaosolymp.chaositems.configuration.action.*
import de.chaosolymp.chaositems.configuration.action.ActionConfiguration
import de.chaosolymp.chaositems.configuration.action.ApplyEffectActionConfiguration
import de.chaosolymp.chaositems.configuration.action.DiamondActionConfiguration
import de.chaosolymp.chaositems.configuration.action.FertilizerActionConfiguration
import de.chaosolymp.chaositems.configuration.action.HealActionConfiguration
import org.bukkit.NamespacedKey

internal sealed class TriggerConfiguration(private var namespacedKey: NamespacedKey) {
    internal abstract fun applyFromMap(map: Map<String, Any?>)
    internal abstract fun applyToMap(map: MutableMap<String, Any?>);

    internal fun toMap(): Map<String, Any?> = buildMap {
        this["key"] = namespacedKey.toString()
        applyToMap(this)
    }

    companion object {
        internal fun fromMap(map: Map<String, Any?>): TriggerConfiguration? {
            val key = map["key"] as String? ?: return null
            val namespacedKey = NamespacedKey.fromString(key) ?: return null

            val configuration = when(namespacedKey) {
                interactTrigger -> InteractTriggerConfiguration()
                else -> return null
            }

            return configuration.apply {
                applyFromMap(map)
            }
        }
    }
}