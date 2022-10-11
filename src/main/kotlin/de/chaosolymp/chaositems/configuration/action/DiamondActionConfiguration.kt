package de.chaosolymp.chaositems.configuration.action

internal class DiamondActionConfiguration: ActionConfiguration(diamonds) {
    var radius: Int? = null

    override fun applyFromMap(map: Map<String, Any?>) {
        radius = map["radius"] as Int?
    }

    override fun applyToMap(map: MutableMap<String, Any?>) {
        map["radius"] = radius
    }
}