package de.chaosolymp.chaositems

import de.chaosolymp.chaositems.command.ChaosItemsCommand
import de.chaosolymp.chaositems.configuration.*
import de.chaosolymp.chaositems.configuration.action.DiamondActionConfiguration
import de.chaosolymp.chaositems.configuration.action.FertilizerActionConfiguration
import de.chaosolymp.chaositems.configuration.action.HealActionConfiguration
import de.chaosolymp.chaositems.configuration.selector.ItemMetaSelector
import de.chaosolymp.chaositems.configuration.selector.ItemSelector
import de.chaosolymp.chaositems.configuration.trigger.InteractTriggerConfiguration
import de.chaosolymp.chaositems.configuration.trigger.MouseButton
import de.chaosolymp.chaositems.event_listener.PlayerListener
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import java.io.File
import java.io.IOException
import kotlin.system.measureTimeMillis

class ItemsPlugin : JavaPlugin {

    // This constructor is required for test-mocking
    constructor() : super()

    // This constructor is required for test-mocking
    constructor(
        loader: JavaPluginLoader,
        description: PluginDescriptionFile,
        dataFolder: File,
        file: File
    ) : super(
        loader, description, dataFolder, file
    )

    internal lateinit var config: PluginConfiguration

    override fun onEnable() {
        measureTimeMillis {
            var config: PluginConfiguration? = null

            try {
                config = loadConfiguration()
            } catch(ex: SecurityException) {
                logger.severe("Your Java runtime uses a (already deprecated) SecurityManager which blocks IO operations.")
            } catch(ex: IOException) {
                logger.severe("An IO error has occurred: ${ex.message}")
            }

            if(config?.messageConfiguration == null || config.items == null) {
                logger.severe("Fatal errors occurred in ${description.name} - Disabling it.")
                server.pluginManager.disablePlugin(this)
                return@onEnable
            }

            val listener = PlayerListener(this)
            server.pluginManager.registerEvents(listener, this)

            val command = getCommand("chaos-items")
            if(command == null) {
                logger.severe("Command `chaos-items` was not found in plugin description file (plugin.yml)")
                logger.severe("Fatal errors occurred in ${description.name} - Disabling it.")
                server.pluginManager.disablePlugin(this)
                return@onEnable
            } else {
                val commandHandler = ChaosItemsCommand(this)
                command.setExecutor(commandHandler)
                command.tabCompleter = commandHandler
            }
        }.let { duration ->
            logger.info("Enabled plugin ${description.name} in ${duration}ms")
        }
    }

    override fun onDisable() {
        measureTimeMillis {
        }.let { duration ->
            logger.info("Disabled plugin ${description.name} in ${duration}ms")
        }
    }

    internal fun loadConfiguration(): PluginConfiguration? {
        if(!dataFolder.exists()) {
            if(!dataFolder.mkdirs()) {
                logger.severe("Cannot create data folder $dataFolder. Please check the file system permissions of your minecraft server.")
                return null
            }
        }

        val file = File(dataFolder, "config.yaml")
        if(!file.exists()) {
            return if(file.createNewFile()) {
                val defaultConfig = getDefaultPluginConfiguration()
                defaultConfig.toConfiguration().save(file)
                logger.info("Created default configuration file")

                config = defaultConfig
                defaultConfig
            } else {
                logger.severe("Cannot create data folder $dataFolder. Please check the file system permissions of your minecraft server.")
                null
            }
        }

        config = PluginConfiguration.fromConfiguration(YamlConfiguration.loadConfiguration(file)).ensureValid(logger)
        return config
    }

    private fun getDefaultPluginConfiguration(): PluginConfiguration = PluginConfiguration().apply {
        items = listOf(
            ItemConfiguration().apply {
                action = HealActionConfiguration()
                selector = ItemSelector().apply {
                    material = Material.CARROT_ON_A_STICK
                    triggers = buildList {
                        add(InteractTriggerConfiguration().apply {
                            mouseButton = MouseButton.Right
                            sneakState = null
                            sprintState = null
                        })
                    }
                    metaSelector = ItemMetaSelector().apply {
                        displayName = Component
                            .text("Ich bin eine Biene")
                            .color(TextColor.color(241, 196, 15))
                        customModelData = null
                    }
                }
            },
            ItemConfiguration().apply {
                action = FertilizerActionConfiguration().apply { radius = 20 }
                triggers = buildList {
                    add(InteractTriggerConfiguration().apply {
                        mouseButton = MouseButton.Right
                        sneakState = null
                        sprintState = null
                    })
                }
                selector = ItemSelector().apply {
                    material = Material.BLAZE_ROD
                    metaSelector = ItemMetaSelector().apply {
                        displayName = Component
                            .text("Wie die Nase eines Mannes so sein Johannes (@Crousus)")
                            .color(TextColor.color(26, 188, 156))
                        customModelData = null
                    }
                }
            },
            ItemConfiguration().apply {
                action = DiamondActionConfiguration().apply { radius = 20 }
                triggers = buildList {
                    add(InteractTriggerConfiguration().apply {
                        mouseButton = MouseButton.Right
                        sneakState = null
                        sprintState = null
                    })
                }
                selector = ItemSelector().apply {
                    material = Material.QUARTZ
                    metaSelector = ItemMetaSelector().apply {
                        displayName = Component
                            .text("Armenischer Diamantenschürfer")
                            .color(TextColor.color(155, 89, 182))
                        customModelData = 1
                    }
                }
            },
        )
        messageConfiguration = MessageConfiguration.defaultConfig
    }

    fun getItems() = sequence {
            config.items?.forEach { item ->
                val itemStack = ItemStack(item.selector?.material!!)
                itemStack.editMeta { meta ->
                    val displayName = item.selector!!.metaSelector?.displayName
                    if(displayName != null) {
                        meta.displayName(displayName)
                    }

                    val customModelData = item.selector!!.metaSelector?.customModelData
                    if(customModelData != null) {
                        meta.setCustomModelData(customModelData)
                    }
                }
                yield(itemStack)
            }

    }
}
