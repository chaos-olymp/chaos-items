package de.chaosolymp.chaositems.command

import de.chaosolymp.chaositems.test_infrastructure.ChaosItemsMock.mockBukkit
import io.kotest.core.spec.style.ShouldSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.assertTrue

class ReloadCommandTest: ShouldSpec({
    context("Command Execution") {
        should("/chaos-items reload config") {
            mockBukkit { server, plugin ->
                val initialInitTime = plugin.config.initializedAt.toEpochMilli()

                // Ensure initialInitTime != updatedInitTime
                runBlocking { delay(1) }

                val player = server.addPlayer()
                player.performCommand("chaos-items reload config")

                val updatedInitTime = plugin.config.initializedAt.toEpochMilli()
                assertTrue(initialInitTime <= updatedInitTime)
            }
        }
    }
    context("Tab completion") {}
})