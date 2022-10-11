package de.chaosolymp.chaositems.test_infrastructure

import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor


object ChaosItemsArb {

    val componentArb = arbitrary {
        val component = Component.text()
        for(extraCount in 0..Arb.int(0, 4).bind()) {
            val subComponent = Component.text()
            subComponent.content(Arb.string().bind())
            if(Arb.boolean().bind()) {
                val r = Arb.int(0, 255).bind()
                val g = Arb.int(0, 255).bind()
                val b = Arb.int(0, 255).bind()
                val color = TextColor.color(r, g, b)
                subComponent.color(color)
            }
            component.append(subComponent)
        }
        component.asComponent()
    }
}