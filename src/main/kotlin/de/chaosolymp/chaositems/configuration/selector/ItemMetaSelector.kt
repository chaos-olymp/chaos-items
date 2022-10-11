package de.chaosolymp.chaositems.configuration.selector

import de.chaosolymp.chaositems.extension.componentFromMap
import de.chaosolymp.chaositems.extension.toMap
import net.kyori.adventure.text.Component
import org.bukkit.inventory.meta.ItemMeta

internal class ItemMetaSelector {
    var displayName: Component? = null
    var customModelData: Int? = null

    internal fun doesMetaMatch(meta: ItemMeta): Boolean {
        if(displayName != null && displayName != meta.displayName()) {
            return false
        }
        if(customModelData != null && (!meta.hasCustomModelData() || customModelData != meta.customModelData)) {
            return false
        }
        return true
    }

    internal fun toMap(): Map<String, Any?> = buildMap {
        this["displayName"] = displayName?.toMap()
        this["customModelData"] = customModelData
    }

    companion object {
        internal fun fromMap(map: Map<String, Any?>): ItemMetaSelector = ItemMetaSelector().apply {
            displayName = componentFromMap(map["displayName"] as? Map<*, *>)
            customModelData = map["customModelData"] as Int?
        }
    }
}