package de.chaosolymp.chaositems.configuration.selector

import de.chaosolymp.chaositems.test_infrastructure.ChaosItemsArb
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.*
import io.kotest.property.checkAll
import io.kotest.property.forAll
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemMetaSelectorTest: StringSpec({
    "ItemMetaSelector.toMap should fill the right values into the map" {
        checkAll(ChaosItemsArb.componentArb) { displayName ->
            checkAll<Int?> {customModelData ->
                val selector = ItemMetaSelector().apply {
                    this.displayName = displayName
                    this.customModelData = customModelData
                }
                val map = selector.toMap()

                val displayNameComponent = map["displayName"] as Map<*, *>

                // First component is in this constellation always empty
                displayNameComponent["text"] shouldBe ""
                map["customModelData"] shouldBe selector.customModelData
            }
        }
    }
    "ItemMetaSelector.fromMap should properly fill the right values into the model" {
        checkAll<Int?, String> {customModelData, displayNameText ->
            val map = buildMap<String, Any?> {
                this["displayName"] = buildMap {
                    this["text"] = displayNameText
                }
                this["customModelData"] = customModelData
            }
            val selector = ItemMetaSelector.fromMap(map)

            val plainTextSerializer = PlainTextComponentSerializer.plainText()
            val serializedDisplayName = plainTextSerializer.serialize(selector.displayName!!)
            serializedDisplayName shouldBe (map["displayName"] as Map<*, *>)["text"]
            selector.customModelData shouldBe map["customModelData"]
        }
    }
})
