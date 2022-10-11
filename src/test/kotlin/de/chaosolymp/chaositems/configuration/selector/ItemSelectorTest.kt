package de.chaosolymp.chaositems.configuration.selector

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.junit.jupiter.api.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals

/*
class ItemSelectorTest {

    @Ignore
    fun `Test toMap`() {
        val selector = ItemSelector().apply {
            material = Material.APPLE
            metaSelector = ItemMetaSelector().apply {
                displayName = Component.text("Test Display Name")
                customModelData = 123
            }
        }

        val map = selector.toMap()
        assertEquals(selector.material.toString(), map["material"])
        assertEquals(selector.metaSelector?.displayName, (map["meta"] as Map<*, *>)["displayName"])
        assertEquals(selector.metaSelector?.customModelData, (map["meta"] as Map<*, *>)["customModelData"])
    }

    @Ignore
    fun `Test fromMap`() {
        val map = buildMap<String, Any?> {
            this["material"] = Material.APPLE.toString()
            this["meta"] = buildMap<String, Any?> {
                this["displayName"] = "Test Display Name"
                this["customModelData"] = null
            }
        }

        val selector = ItemSelector.fromMap(map)
        assertEquals(map["material"], selector.material)
        assertEquals((map["meta"] as Map<*, *>)["displayName"], selector.metaSelector?.displayName)
        assertEquals((map["meta"] as Map<*, *>)["customModelData"], selector.metaSelector?.customModelData)
    }
}*/