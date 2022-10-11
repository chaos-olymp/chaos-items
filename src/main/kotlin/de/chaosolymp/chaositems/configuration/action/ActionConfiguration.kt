package de.chaosolymp.chaositems.configuration.action

import de.chaosolymp.chaositems.item.action.*
import org.bukkit.NamespacedKey

internal sealed class ActionConfiguration(private var namespacedKey: NamespacedKey) {
    internal abstract fun applyFromMap(map: Map<String, Any?>)
    internal abstract fun applyToMap(map: MutableMap<String, Any?>);

    internal fun toMap(): Map<String, Any?> = buildMap {
        this["key"] = namespacedKey.toString()
        applyToMap(this)
    }

    internal fun createAction(): ItemAction {
        return when(this) {
            is ApplyEffectActionConfiguration -> ApplyEffectAction(this)
            is HealActionConfiguration -> HealAction
            is DiamondActionConfiguration -> DiamondAction(this.radius!!)
            is FertilizerActionConfiguration -> FertilizerAction(this.radius!!)
        }
    }

    companion object {
        internal fun fromMap(map: Map<String, Any?>): ActionConfiguration? {
            val key = map["key"] as String? ?: return null
            val namespacedKey = NamespacedKey.fromString(key) ?: return null

            val configuration = when(namespacedKey) {
                applyEffects -> ApplyEffectActionConfiguration()
                heal -> HealActionConfiguration()
                diamonds -> DiamondActionConfiguration()
                fertilizer -> FertilizerActionConfiguration()
                else -> return null
            }

            return configuration.apply {
                applyFromMap(map)
            }
        }
    }
}