package de.chaosolymp.chaositems.configuration.selector

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

internal class ItemSelector {
    var material: Material? = null
    var metaSelector: ItemMetaSelector? = null

    internal fun doesMatch(item: ItemStack): Boolean {
        if(material != null && material != item.type) {
            return false
        }
        if(metaSelector != null && item.itemMeta != null) {
            return metaSelector!!.doesMetaMatch(item.itemMeta!!)
        }
        return true
    }

    internal fun toMap(): Map<String, Any?> = buildMap {
        this["material"] = material?.toString()
        this["meta"] = metaSelector?.toMap()
    }

    companion object {
        internal fun fromMap(map: Map<String, Any?>): ItemSelector = ItemSelector().apply {
            this.material = (map["material"] as String?)?.let { return@let Material.valueOf(it) }
            this.metaSelector = (map["meta"] as Map<String, Any?>?)?.let { return@let ItemMetaSelector.fromMap(it) }
        }
    }
}