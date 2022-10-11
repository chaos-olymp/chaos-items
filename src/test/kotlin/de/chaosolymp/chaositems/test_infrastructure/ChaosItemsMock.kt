package de.chaosolymp.chaositems.test_infrastructure

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import de.chaosolymp.chaositems.ItemsPlugin

object ChaosItemsMock {
    suspend inline fun mockBukkit(crossinline fn: suspend (server: ServerMock, plugin: ItemsPlugin) -> Unit) {
        try {
            val server = MockBukkit.mock()
            MockBukkit.ensureMocking()
            val plugin = MockBukkit.load(ItemsPlugin::class.java)
            fn(server, plugin)
        } finally {
            MockBukkit.unmock()
        }
    }
}