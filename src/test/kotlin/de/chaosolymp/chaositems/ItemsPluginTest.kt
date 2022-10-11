package de.chaosolymp.chaositems

import de.chaosolymp.chaositems.test_infrastructure.ChaosItemsMock
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.ShouldSpec
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File

class ItemsPluginTest: ShouldSpec({
    context("ItemsPlugin() - parameterless constructor") {
        should("throw IllegalStateException") {
            shouldThrowWithMessage<IllegalStateException>("JavaPlugin requires org.bukkit.plugin.java.PluginClassLoader") {
                ItemsPlugin()
            }
        }
    }
    context("ItemsPlugin() - constructor") {
        should("not throw") {
            ChaosItemsMock.mockBukkit { _, plugin ->
                ItemsPlugin(plugin.pluginLoader as JavaPluginLoader, plugin.description, plugin.dataFolder, File("mock"))
            }
        }
    }
})