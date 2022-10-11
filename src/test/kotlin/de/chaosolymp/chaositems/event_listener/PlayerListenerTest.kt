package de.chaosolymp.chaositems.event_listener

import de.chaosolymp.chaositems.test_infrastructure.ChaosItemsMock
import io.kotest.core.spec.style.ShouldSpec
import kotlin.test.todo

class PlayerListenerTest : ShouldSpec({
    context("handleRightClick") {
        should("do nothing if itemOnCursor is AIR") {
            todo {
                ChaosItemsMock.mockBukkit { _, _ ->
                }
            }
        }
        should("do nothing if itemOnCursor does not match any selector") {
            todo {
                ChaosItemsMock.mockBukkit { _, _ ->
                }
            }
        }
        should("do nothing if itemOnCursor does match any selector") {
            todo {
                ChaosItemsMock.mockBukkit { _, _ ->
                }
            }
        }
    }
})
