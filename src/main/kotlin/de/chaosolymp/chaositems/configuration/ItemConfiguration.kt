package de.chaosolymp.chaositems.configuration

import de.chaosolymp.chaositems.configuration.action.ActionConfiguration
import de.chaosolymp.chaositems.configuration.selector.ItemSelector
import de.chaosolymp.chaositems.configuration.trigger.TriggerConfiguration

internal class ItemConfiguration {
    internal var action: ActionConfiguration? = null
    internal var selector: ItemSelector? = null
    internal var triggers: List<TriggerConfiguration>? = null

    internal fun toMap(): Map<String, Any?> = buildMap {
        this["action"] = action?.toMap()
        this["selector"] = selector?.toMap()
        this["trigger"] = triggers?.map { trigger -> trigger.toMap() } ?: listOf<Map<String, Any?>>()
    }

    companion object {
        internal fun fromMap(map: Map<String, Any?>): ItemConfiguration = ItemConfiguration().apply {
            this.action = (map["action"] as Map<String, Any?>?)?.let { return@let ActionConfiguration.fromMap(it) }
            this.selector = (map["selector"] as Map<String, Any?>?)?.let { return@let ItemSelector.fromMap(it) }
            this.triggers = (map["trigger"] as List<Map<String, Any?>>).map { trigger -> TriggerConfiguration.fromMap(trigger)!! }
        }
    }
}