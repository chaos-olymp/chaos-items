package de.chaosolymp.chaositems.configuration.trigger

import java.util.*

internal class InteractTriggerConfiguration : TriggerConfiguration(interactTrigger) {
    var mouseButton: MouseButton? = null
    var sneakState: Boolean? = null
    var sprintState: Boolean? = null

    override fun applyFromMap(map: Map<String, Any?>) {
        val button = map["mouseButton"] as String?
        mouseButton = when(button?.lowercase(Locale.getDefault())) {
            null -> null
            "left" -> MouseButton.Left
            "right" -> MouseButton.Right
            else -> throw IllegalArgumentException("mouseButton must be null, left or right")
        }
        sneakState = map["sneakState"] as Boolean?
        sprintState = map["sprintState"] as Boolean?
    }

    override fun applyToMap(map: MutableMap<String, Any?>) {
        map["mouseButton"] = when(mouseButton) {
            null -> null
            MouseButton.Left -> "left"
            MouseButton.Right -> "right"
        }
        map["sneakState"] = sneakState
        map["sprintState"] = sprintState
    }
}